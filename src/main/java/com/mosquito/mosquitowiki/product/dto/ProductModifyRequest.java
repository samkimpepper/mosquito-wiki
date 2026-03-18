package com.mosquito.mosquitowiki.product.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductModifyRequest {
    private String name;
    private String nameKo;
    private String option;
    private String optionKo;
    private String description;
    private List<TagRequest> addTags;
    private List<Long> removeTags;
    private List<String> imageSlots;
}
