package com.mosquito.mosquitowiki.product.dto;

import com.mosquito.mosquitowiki.product.domain.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductSearchResponse {
    private String slug;
    private String name;
    private String nameKo;
    private String image;

    public static ProductSearchResponse of(Product product) {
        return ProductSearchResponse.builder()
                .slug(product.getSlug())
                .name(product.getName())
                .nameKo(product.getNameKo())
                .image(product.getOfficialImageUrl())
                .build();
    }
}
