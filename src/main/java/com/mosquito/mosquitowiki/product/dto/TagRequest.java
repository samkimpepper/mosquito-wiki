package com.mosquito.mosquitowiki.product.dto;

import com.mosquito.mosquitowiki.product.domain.Tag;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagRequest {
    private Long id;
    private String tagType;
    private String tagValue;
    private String color;

    public static TagRequest of(Tag tag) {
        return TagRequest.builder()
                .id(tag.getId())
                .tagType(tag.getTagType())
                .tagValue(tag.getTagValue())
                .color(tag.getColor())
                .build();
    }
}
