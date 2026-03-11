package com.mosquito.mosquitowiki.product.dto;

import com.mosquito.mosquitowiki.product.domain.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CategoryResponse {
    private String name;
    private String slug;

    public static CategoryResponse from(Category category) {
        return CategoryResponse.builder()
                .name(category.getName())
                .slug(category.getSlug())
                .build();
    }
}
