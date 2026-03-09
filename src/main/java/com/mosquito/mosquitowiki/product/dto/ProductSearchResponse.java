package com.mosquito.mosquitowiki.product.dto;

import com.mosquito.mosquitowiki.product.domain.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductSearchResponse {
    private Long id;
    private String name;
    private String officialImageUrl;

    public static ProductSearchResponse of(Product product) {
        return ProductSearchResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .officialImageUrl(product.getOfficialImageUrl())
                .build();
    }
}
