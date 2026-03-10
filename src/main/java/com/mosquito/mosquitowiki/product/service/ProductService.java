package com.mosquito.mosquitowiki.product.service;

import com.mosquito.mosquitowiki.exception.BaseException;
import com.mosquito.mosquitowiki.exception.ErrorCode;
import com.mosquito.mosquitowiki.file.FileService;
import com.mosquito.mosquitowiki.product.domain.Brand;
import com.mosquito.mosquitowiki.product.domain.Product;
import com.mosquito.mosquitowiki.product.dto.ProductCreateRequest;
import com.mosquito.mosquitowiki.product.dto.ProductSearchResponse;
import com.mosquito.mosquitowiki.product.repository.BrandRepository;
import com.mosquito.mosquitowiki.product.repository.ProductRepository;
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
    private final FileService fileService;

    @Transactional(readOnly = true)
    public List<ProductSearchResponse> search(String query) {
        return productRepository.searchByName(query).stream()
                .map(ProductSearchResponse::of)
                .toList();
    }

    public String save(ProductCreateRequest request, MultipartFile image) {
        Brand brand = brandRepository.findBySlug(request.getBrandSlug()).orElseThrow(() -> new BaseException(ErrorCode.BRAND_NOT_FOUND));
        String brandName = brand.getName();         // Anastasia Beverly Hills
        String productName = request.getName();     // Highlighter
        String optionName = request.getOption();    // Iced out

        String brandNameKo = brand.getNameKo();     // 아나스타샤 베버리 힐즈
        String productNameKo = request.getNameKo();   // 하이라이터
        String optionNameKo = request.getOptionKo();  // 아이스드아웃

        Product parent = null;
        if (optionName != null) {
            String parentSlug = SlugUtil.toSlug(brandName + " " + productName);

            // 이미 부모 제품 있으면 가져오고, 없으면 생성
            parent = productRepository.findBySlug(parentSlug)
                    .orElseGet(() -> productRepository.save(Product.builder()
                            .brand(brand)
                            .category(null)
                            .name(brandName + " " + productName)
                            .nameKo(brandNameKo + " " + productNameKo)
                            .slug(parentSlug)
                            .build()));
        }

        String imageUrl = null;
        if (image!= null && image.isEmpty()) {
            try {
                imageUrl = fileService.save(image);
            } catch (IOException e) {
                throw new BaseException(ErrorCode.FILE_UPLOAD_ERROR);
            }
        }

        // 실제 제품 등록
        String slug = optionName != null
                ? SlugUtil.toSlug(brandName + " " + productName + " " + optionName)
                : SlugUtil.toSlug(brandName + " " + productName);

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
                .slug(slug)
                .officialImageUrl(imageUrl)
                .createdAt(LocalDateTime.now())
                .build());

        return product.getSlug();

    }
}
