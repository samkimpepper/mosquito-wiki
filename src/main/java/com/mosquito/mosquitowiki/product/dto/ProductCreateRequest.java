package com.mosquito.mosquitowiki.product.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ProductCreateRequest {
    private Long brandId;
    private String name;
    private String nameKo;
    private String option;
    private String optionKo;
    private MultipartFile image;
    private String description;
}
