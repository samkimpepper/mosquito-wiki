package com.mosquito.mosquitowiki.product.dto;

import com.mosquito.mosquitowiki.product.domain.Brand;
import com.mosquito.mosquitowiki.product.domain.Product;
import com.mosquito.mosquitowiki.product.domain.Tag;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ProductDetailResponse {
    private String slug;
    private String brandSlug;
    private String category;
    private String categorySlug;
    private String brandName;
    private String name;
    private String optionName;
    private String brandNameKo;
    private String nameKo;
    private String optionNameKo;
    private String description;
    private String officialImageUrl;
    private List<TagRequest> tags;

    public static ProductDetailResponse from(Product product, Brand brand, List<Tag> tags) {
        return ProductDetailResponse.builder()
                .slug(product.getSlug())
                .brandSlug(brand.getSlug())
                .category(product.getCategory().getName())
                .categorySlug(product.getCategory().getSlug())
                .brandName(brand.getName())
                .name(product.getName())
                .optionName(product.getOptionName())
                .brandNameKo(brand.getNameKo())
                .nameKo(product.getNameKo())
                .optionNameKo(product.getOptionNameKo())
                .description(product.getDescription())
                .officialImageUrl(product.getOfficialImageUrl())
                .tags(tags.stream().map(TagRequest::of).toList())
                .build();
    }
}
