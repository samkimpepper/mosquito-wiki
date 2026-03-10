package com.mosquito.mosquitowiki.product.dto;

import com.mosquito.mosquitowiki.product.domain.Brand;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BrandDetailResponse {
    private String slug;
    private String name;
    private String nameKo;
    private String logoUrl;

    public static BrandDetailResponse from(Brand brand) {
        return BrandDetailResponse.builder()
                .slug(brand.getSlug())
                .name(brand.getName())
                .nameKo(brand.getNameKo())
                .logoUrl(brand.getLogoUrl())
                .build();
    }
}
