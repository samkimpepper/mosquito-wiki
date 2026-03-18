package com.mosquito.mosquitowiki.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCreateRequest {
    private String brandSlug;
    private String name;
    private String nameKo;
    private String parentProductSlug;
    private String option;
    private String optionKo;
    private String categorySlug;
    private String description;
}
