package com.mosquito.mosquitowiki.product.dto;

import com.mosquito.mosquitowiki.product.domain.Brand;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BrandSearchResponse {
    private Long id;
    private String name;
    private String nameKo;
    private String logoUrl;

    public static BrandSearchResponse of(Brand brand) {
        return BrandSearchResponse.builder()
                .id(brand.getId())
                .name(brand.getName())
                .nameKo(brand.getNameKo())
                .logoUrl(brand.getLogoUrl())
                .build();
    }
}
