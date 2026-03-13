package com.mosquito.mosquitowiki.product.dto;

import com.mosquito.mosquitowiki.product.domain.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductCardResponse {
    private String slug;
    private String officialImageUrl;
    private String name;
    private String nameKo;
    private String optionName;
    private String optionNameKo;
    private Boolean isCurrent;
    private Boolean isParent;

    public static ProductCardResponse from(Product product, String currentSlug) {
        return ProductCardResponse.builder()
                .slug(product.getSlug())
                .officialImageUrl(product.getOfficialImageUrl())
                .name((product.getParent() == null) ? product.getName() : product.getParent().getName())
                .nameKo((product.getParent() == null) ? product.getNameKo() : product.getParent().getNameKo())
                .optionName(product.getOptionName())
                .optionNameKo(product.getOptionNameKo())
                .isCurrent(product.getSlug().equals(currentSlug))
                .isParent(product.getParent() == null)
                .build();
    }
}
