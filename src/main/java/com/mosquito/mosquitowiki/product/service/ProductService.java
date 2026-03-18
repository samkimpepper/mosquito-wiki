package com.mosquito.mosquitowiki.product.service;

import com.mosquito.mosquitowiki.exception.BaseException;
import com.mosquito.mosquitowiki.exception.ErrorCode;
import com.mosquito.mosquitowiki.file.FileService;
import com.mosquito.mosquitowiki.product.domain.*;
import com.mosquito.mosquitowiki.product.dto.*;
import com.mosquito.mosquitowiki.product.repository.*;
import com.mosquito.mosquitowiki.users.User;
import com.mosquito.mosquitowiki.utils.SlugUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final TagRepository tagRepository;
    private final ProductTagRepository productTagRepository;
    private final CategoryRepository categoryRepository;
    private final FileService fileService;
    private final ProductLikeRepository productLikeRepository;

    @Transactional(readOnly = true)
    public List<ProductSearchResponse> search(String query) {
        return productRepository.searchByName(query).stream()
                .map(ProductSearchResponse::of)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductSearchResponse info(String slug) {
        return ProductSearchResponse.of(productRepository.findBySlug(slug).orElseThrow(() -> new BaseException(ErrorCode.PRODUCT_NOT_FOUND)));
    }

    public String save(ProductCreateRequest request, List<MultipartFile> images, User user) {
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
            String parentSlug;
            if (request.getParentProductSlug() != null) {
                parentSlug = request.getParentProductSlug();
            } else {
                parentSlug = SlugUtil.toSlug(brandName + " " + productNameKo);
            }

            // 이미 부모 제품 있으면 가져오고, 없으면 생성
            parent = productRepository.findBySlug(parentSlug)
                    .orElseGet(() -> productRepository.save(Product.builder()
                            .brand(brand)
                            .category(null)
                            .name(productName)
                            .nameKo(productNameKo)
                            .fullName(brandName + " " + productName)
                            .fullNameKo(brandNameKo + " " + productNameKo)
                            .slug(parentSlug)
                            .category(category)
                            .createdAt(LocalDateTime.now())
                            .modifiedAt(LocalDateTime.now())
                            .createdBy(user)
                            .build()));
        }

        List<String> imageUrls = new ArrayList<>();
        if (images != null && images.size() > 4) {
            throw new BaseException(ErrorCode.TOO_MANY_IMAGES);
        }
        if (images!= null && !images.isEmpty()) {
            try {
                for (MultipartFile file : images) {
                    imageUrls.add(fileService.save(file));
                }
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
                .optionName(optionName)
                .optionNameKo(optionNameKo)
                .fullName(brand.getName() + " " + productName + " " + optionName)
                .fullNameKo(brandNameKo + " " + productNameKo + " " + optionNameKo)
                .slug(slug)
                .category(category)
                .officialImageUrls(imageUrls)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .createdBy(user)
                .build());

        return product.getSlug();
    }

    @Transactional
    public ProductDetailResponse modify(String slug, ProductModifyRequest request, List<MultipartFile> newImages, User user) {
        Product product = productRepository.findBySlug(slug).orElseThrow(() -> new BaseException(ErrorCode.PRODUCT_NOT_FOUND));

        if (newImages != null && newImages.size() > 4) {
            throw new BaseException(ErrorCode.TOO_MANY_IMAGES);
        }
        try {
            List<String> newImageUrls = resolveImageUrl(request.getImageSlots(), newImages, product.getOfficialImageUrls());
            product.updateImage(newImageUrls);

        } catch (IOException e) {
            throw new BaseException(ErrorCode.FILE_UPLOAD_ERROR);
        }

        product.update(request, user);

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
        List<ProductCardResponse> otherOptions = productRepository.findAllByParent(product.getParent())
                .stream()
                .map(p -> ProductCardResponse.from(p, slug))
                .sorted(Comparator.comparing(ProductCardResponse::getIsCurrent).reversed())
                .toList();

        long likeCount = productLikeRepository.countByProduct(product);
        boolean liked = productLikeRepository.existsByProductAndUser(product, user);

        return ProductDetailResponse.from(product, product.getBrand(), liked, likeCount, currentTags, otherOptions);

    }

    @Transactional(readOnly = true)
    public ProductDetailResponse detail(String slug, User user) {
        Product product = productRepository.findBySlug(slug).orElseThrow(() -> new BaseException(ErrorCode.PRODUCT_NOT_FOUND));
        Brand brand = product.getBrand();

        List<ProductTag> productTags = productTagRepository.findByProductId(product.getId());
        List<Tag> tags = productTags.stream().map(tag -> tag.getTag()).toList();

        List<Product> products = productRepository.findAllByParent(product.getParent());
        products.add(product.getParent());  // parent 추가

        List<ProductCardResponse> otherOptions = products.stream()
                .map(p -> ProductCardResponse.from(p, slug))
                .sorted(Comparator.comparing(ProductCardResponse::getIsCurrent).reversed())
                .collect(Collectors.toList());

        long likeCount = productLikeRepository.countByProduct(product);
        boolean liked = productLikeRepository.existsByProductAndUser(product, user);

        return ProductDetailResponse.from(product, brand, liked, likeCount, tags, otherOptions);
    }

    @Transactional
    public LikeResponse toggleLike(String slug, User user) {
        Product product = productRepository.findBySlug(slug).orElseThrow(() -> new BaseException(ErrorCode.PRODUCT_NOT_FOUND));

        boolean liked = productLikeRepository.existsByProductAndUser(product, user);

        if (liked) {
            productLikeRepository.deleteByProductAndUser(product, user);
        } else {
            productLikeRepository.save(ProductLike.create(product, user));
        }

        return LikeResponse.builder()
                .liked(!liked)
                .likeCount(productLikeRepository.countByProduct(product))
                .build();
    }

    public boolean isKorean(String str) {
        return str.matches(".*[\\uAC00-\\uD7A3\\u3131-\\u318E]+.*");
    }

    private List<String> resolveImageUrl(List<String> imageSlots, List<MultipartFile> newImages, List<String> oldImageUrls) throws IOException {
        List<String> finalImageUrls = new ArrayList<>();

        for (String slot : imageSlots) {
            if (slot.startsWith("NEW_")) {
                int idx = Integer.parseInt(slot.substring(4));
                finalImageUrls.add(fileService.save(newImages.get(idx)));
            } else {
                finalImageUrls.add(slot); // 기존 URL 유지
            }
        }

        if (finalImageUrls.size() > 4) {
            throw new BaseException(ErrorCode.TOO_MANY_IMAGES);
        }

        for (String oldUrl : oldImageUrls) {
            if (!finalImageUrls.contains(oldUrl)) {
                fileService.delete(oldUrl);
            }
        }

        return finalImageUrls;
    }
}
