package com.mosquito.mosquitowiki.product.service;

import com.mosquito.mosquitowiki.exception.BaseException;
import com.mosquito.mosquitowiki.exception.ErrorCode;
import com.mosquito.mosquitowiki.file.FileService;
import com.mosquito.mosquitowiki.product.domain.Brand;
import com.mosquito.mosquitowiki.product.domain.Product;
import com.mosquito.mosquitowiki.product.dto.BrandCreateRequest;
import com.mosquito.mosquitowiki.product.dto.BrandDetailResponse;
import com.mosquito.mosquitowiki.product.dto.BrandSearchResponse;
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
public class BrandService {
    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;
    private final FileService fileService;

    public String save(BrandCreateRequest request, MultipartFile image) {
        String slug = SlugUtil.toSlug(request.getNameKo());
        if (brandRepository.existsBySlug(slug)) {
            throw new BaseException(ErrorCode.BRAND_ALREADY_EXISTS);
        }

        String imageUrl = null;
        if (image!= null && !image.isEmpty()) {
            try {
                imageUrl = fileService.save(image);
            } catch (IOException e) {
                throw new BaseException(ErrorCode.FILE_UPLOAD_ERROR);
            }
        }

        brandRepository.save(Brand.builder()
                .name(request.getName())
                .nameKo(request.getNameKo())
                .slug(slug)
                .logoUrl(imageUrl)
                .createdAt(LocalDateTime.now())
                .build());

        return slug;
    }

    @Transactional(readOnly = true)
    public List<BrandSearchResponse> search(String query) {
        return brandRepository.searchByName(query).stream()
                .map(BrandSearchResponse::of)
                .toList();
    }

    @Transactional(readOnly = true)
    public BrandDetailResponse showDetail(String slug) {
        Brand brand = brandRepository.findBySlug(slug).orElseThrow(() -> new BaseException(ErrorCode.BRAND_NOT_FOUND));
        List<Product> products = productRepository.findAllByBrand(brand);

        return BrandDetailResponse.from(brand, products);
    }
}
