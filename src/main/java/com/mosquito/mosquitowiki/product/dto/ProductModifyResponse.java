package com.mosquito.mosquitowiki.product.dto;

import com.mosquito.mosquitowiki.product.domain.Product;
import com.mosquito.mosquitowiki.product.domain.Tag;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ProductModifyResponse {
    private String slug;
    private String name;
    private String nameKo;
    private String option;
    private String optionKo;
    private String officialImageUrl;
    private String description;
    private List<TagRequest> tags;

    public static ProductModifyResponse from(Product product, List<Tag> tags) {
        return ProductModifyResponse.builder()
                .slug(product.getSlug())
                .name(product.getName())
                .nameKo(product.getNameKo())
                .option(product.getOptionName())
                .optionKo(product.getOptionNameKo())
                .officialImageUrl(product.getOfficialImageUrl())
                .description(product.getDescription())
                .tags(tags.stream().map(TagRequest::of).toList())
                .build();
    }
}
