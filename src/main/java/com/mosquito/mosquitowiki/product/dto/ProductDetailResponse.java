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
    private String brandLogoUrl;
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
    private long likeCount;
    private List<TagRequest> tags;
    private List<ProductCardResponse> otherOptions;

    public static ProductDetailResponse from(Product product, Brand brand, long likeCount, List<Tag> tags, List<ProductCardResponse> otherOptions) {
        return ProductDetailResponse.builder()
                .slug(product.getSlug())
                .brandSlug(brand.getSlug())
                .brandLogoUrl(brand.getLogoUrl())
                .category(product.getCategory().getName())
                .categorySlug(product.getCategory().getSlug())
                .brandName(brand.getName())
                .name((product.getParent() == null) ? product.getName() : product.getParent().getName())
                .optionName(product.getOptionName())
                .brandNameKo(brand.getNameKo())
                .nameKo((product.getParent() == null) ? product.getNameKo() : product.getParent().getNameKo())
                .optionNameKo(product.getOptionNameKo())
                .description(product.getDescription())
                .officialImageUrl(product.getOfficialImageUrl())
                .likeCount(likeCount)
                .tags(tags.stream().map(TagRequest::of).toList())
                .otherOptions(otherOptions)
                .build();
    }
}
