package com.mosquito.mosquitowiki.product.dto;

import com.mosquito.mosquitowiki.product.domain.Product;
import com.mosquito.mosquitowiki.product.domain.Tag;
import com.mosquito.mosquitowiki.users.UserThumbnailResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PopularProductResponse {
    private String slug;
    private List<String> officialImageUrls;
    private String name;
    private String nameKo;
    private String optionName;
    private String optionNameKo;
    private String brandSlug;
    private String brandLogoUrl;
    private String brandName;
    private String brandNameKo;
    private long swatchCount;
    private long likeCount;
    private long viewCount;
    private List<TagRequest> tags;
    private String categorySlug;
    private String categoryName;

    public static PopularProductResponse of(Product product, List<Tag> tags) {
        return PopularProductResponse.builder()
                .slug(product.getSlug())
                .officialImageUrls(product.getOfficialImageUrls())
                .name((product.getParent() == null) ? product.getName() : product.getParent().getName())
                .nameKo((product.getParent() == null) ? product.getNameKo() : product.getParent().getNameKo())
                .optionName(product.getOptionName())
                .optionNameKo(product.getOptionNameKo())
                .brandLogoUrl(product.getBrand().getLogoUrl())
                .brandSlug(product.getBrand().getSlug())
                .brandName(product.getBrand().getName())
                .brandNameKo(product.getBrand().getNameKo())
                .swatchCount(0)
                .likeCount(product.getLikeCount())
                .viewCount(product.getViewCount())
                .tags(tags.stream().map(TagRequest::of).toList())
                .categoryName(product.getCategory().getName())
                .categorySlug(product.getCategory().getSlug())
                .build();
    }
}
