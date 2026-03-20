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
    private int swatchCount;
    private String categorySlug;

    public static ProductSearchResponse of(Product product) {
        return ProductSearchResponse.builder()
                .slug(product.getSlug())
                .name((product.getParent() == null) ? product.getName() : product.getParent().getName() + " " + product.getOptionName())
                .nameKo((product.getParent() == null) ? product.getNameKo() : product.getParent().getNameKo() + " " + product.getOptionNameKo())
                .image(product.getOfficialImageUrl())
                .swatchCount(0)
                .categorySlug(product.getCategory().getSlug())
                .build();
    }
}
