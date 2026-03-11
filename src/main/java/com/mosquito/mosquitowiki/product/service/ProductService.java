package com.mosquito.mosquitowiki.product.service;

import com.mosquito.mosquitowiki.exception.BaseException;
import com.mosquito.mosquitowiki.exception.ErrorCode;
import com.mosquito.mosquitowiki.file.FileService;
import com.mosquito.mosquitowiki.product.domain.*;
import com.mosquito.mosquitowiki.product.dto.*;
import com.mosquito.mosquitowiki.product.repository.*;
import com.mosquito.mosquitowiki.utils.SlugUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final TagRepository tagRepository;
    private final ProductTagRepository productTagRepository;
    private final CategoryRepository categoryRepository;
    private final FileService fileService;

    @Transactional(readOnly = true)
    public List<ProductSearchResponse> search(String query) {
        return productRepository.searchByName(query).stream()
                .map(ProductSearchResponse::of)
                .toList();
    }

    public String save(ProductCreateRequest request, MultipartFile image) {
        Brand brand = brandRepository.findBySlug(request.getBrandSlug()).orElseThrow(() -> new BaseException(ErrorCode.BRAND_NOT_FOUND));
        String brandName = brand.getSlug();         // Anastasia Beverly Hills
        String productName = request.getName();     // Highlighter
        String optionName = request.getOption();    // Iced out

        String brandNameKo = brand.getNameKo();     // 아나스타샤 베버리 힐즈
        String productNameKo = request.getNameKo();   // 하이라이터
        String optionNameKo = request.getOptionKo();  // 아이스드아웃

        Category category = categoryRepository.findBySlug(request.getCategorySlug()).orElseThrow();

        Product parent = null;
        if (optionName != null) {
            String parentSlug = SlugUtil.toSlug(brandName + " " + productNameKo);

            // 이미 부모 제품 있으면 가져오고, 없으면 생성
            parent = productRepository.findBySlug(parentSlug)
                    .orElseGet(() -> productRepository.save(Product.builder()
                            .brand(brand)
                            .category(null)
                            .name(productName + " " + optionName)
                            .nameKo(productNameKo + " " + optionNameKo)
                            .fullName(brandName + " " + productName)
                            .fullNameKo(brandNameKo + " " + productNameKo)
                            .slug(parentSlug)
                            .category(category)
                            .createdAt(LocalDateTime.now())
                            .build()));
        }

        String imageUrl = null;
        if (image!= null && !image.isEmpty()) {
            try {
                imageUrl = fileService.save(image);
            } catch (IOException e) {
                throw new BaseException(ErrorCode.FILE_UPLOAD_ERROR);
            }
        }

        // 실제 제품 등록
        String slug = optionName != null
                ? SlugUtil.toSlug(brandName + " " + productNameKo + " " + optionNameKo)
                : SlugUtil.toSlug(brandName + " " + productNameKo);

        if (productRepository.existsBySlug(slug)) {
            throw new BaseException(ErrorCode.PRODUCT_ALREADY_EXISTS);
        }

        Product product = productRepository.save(Product.builder()
                .brand(brand)
                .category(null)
                .parent(parent)
                .name(optionName != null
                        ? brandName + " " + productName + " " + optionName
                        : brandName + " " + productName)
                .nameKo(optionNameKo != null
                        ? brandNameKo + " " + productNameKo + " " + optionNameKo
                        : brandNameKo + " " + productNameKo)
                .fullName(brand.getName() + " " + productName + " " + optionName)
                .fullNameKo(brandNameKo + " " + productNameKo + " " + optionNameKo)
                .slug(slug)
                .category(category)
                .officialImageUrl(imageUrl)
                .createdAt(LocalDateTime.now())
                .build());

        return product.getSlug();
    }

    @Transactional
    public ProductModifyResponse modify(String slug, ProductModifyRequest request, MultipartFile image) {
        Product product = productRepository.findBySlug(slug).orElseThrow(() -> new BaseException(ErrorCode.BRAND_NOT_FOUND));

        String imageUrl = null;
        if (image!= null && image.isEmpty()) {
            try {
                imageUrl = fileService.save(image);
            } catch (IOException e) {
                throw new BaseException(ErrorCode.FILE_UPLOAD_ERROR);
            }
        }

        product.update(request, imageUrl);

        List<Tag> tags;
        if (!request.getAddTags().isEmpty()) {
            tags = request.getAddTags().stream().map(Tag::create).toList();
            tagRepository.saveAll(tags);

            List<ProductTag> productTags = tags.stream()
                    .map(tag -> ProductTag.create(product, tag))
                    .toList();
            productTagRepository.saveAll(productTags);
        }

        if (!request.getRemoveTags().isEmpty()) {
            productTagRepository.deleteByProductAndTagIdIn(product, request.getRemoveTags());
        }

        List<ProductTag> currentProductTags = productTagRepository.findByProductId(product.getId());
        List<Tag> currentTags = currentProductTags.stream().map(tag -> tag.getTag()).toList();

        return ProductModifyResponse.from(product, currentTags);

    }

    @Transactional(readOnly = true)
    public ProductDetailResponse detail(String slug) {
        Product product = productRepository.findBySlug(slug).orElseThrow(() -> new BaseException(ErrorCode.BRAND_NOT_FOUND));
        Brand brand = product.getBrand();

        List<ProductTag> productTags = productTagRepository.findByProductId(product.getId());
        List<Tag> tags = productTags.stream().map(tag -> tag.getTag()).toList();

        return ProductDetailResponse.from(product, brand, tags);
    }

    public boolean isKorean(String str) {
        return str.matches(".*[\\uAC00-\\uD7A3\\u3131-\\u318E]+.*");
    }
}
