package com.mosquito.mosquitowiki.product.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class BrandCreateRequest {
    private String name;
    private String nameKo;
    private MultipartFile image;
}
