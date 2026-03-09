package com.mosquito.mosquitowiki.product.service;

import com.mosquito.mosquitowiki.exception.BaseException;
import com.mosquito.mosquitowiki.exception.ErrorCode;
import com.mosquito.mosquitowiki.file.FileService;
import com.mosquito.mosquitowiki.product.domain.Brand;
import com.mosquito.mosquitowiki.product.dto.BrandCreateRequest;
import com.mosquito.mosquitowiki.product.dto.BrandSearchResponse;
import com.mosquito.mosquitowiki.product.repository.BrandRepository;
import com.mosquito.mosquitowiki.utils.SlugUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;
    private final FileService fileService;

    public Long save(BrandCreateRequest request) {
        String slug = SlugUtil.toSlug(request.getName());
        if (brandRepository.existsBySlug(slug)) {
            throw new BaseException(ErrorCode.BRAND_ALREADY_EXISTS);
        }

        String imageUrl = null;
        if (request.getImage() != null && !request.getImage().isEmpty()) {
            try {
                imageUrl = fileService.save(request.getImage());
            } catch (IOException e) {
                throw new BaseException(ErrorCode.FILE_UPLOAD_ERROR);
            }
        }

        return brandRepository.save(Brand.builder()
                .name(request.getName())
                .nameKo(request.getNameKo())
                .slug(slug)
                .logoUrl(imageUrl)
                .build())
                .getId();
    }

    @Transactional(readOnly = true)
    public List<BrandSearchResponse> search(String query) {
        return brandRepository.searchByName(query).stream()
                .map(BrandSearchResponse::of)
                .toList();
    }

}
