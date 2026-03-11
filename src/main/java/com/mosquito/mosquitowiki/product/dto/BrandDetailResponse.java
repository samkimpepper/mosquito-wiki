package com.mosquito.mosquitowiki.product.dto;

import com.mosquito.mosquitowiki.product.domain.Brand;
import com.mosquito.mosquitowiki.product.domain.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class BrandDetailResponse {
    private String slug;
    private String name;
    private String nameKo;
    private String logoUrl;
    List<ProductSearchResponse> products;

    public static BrandDetailResponse from(Brand brand, List<Product> products) {
        return BrandDetailResponse.builder()
                .slug(brand.getSlug())
                .name(brand.getName())
                .nameKo(brand.getNameKo())
                .logoUrl(brand.getLogoUrl())
                .products(products.stream().map(ProductSearchResponse::of).toList())
                .build();
    }
}
