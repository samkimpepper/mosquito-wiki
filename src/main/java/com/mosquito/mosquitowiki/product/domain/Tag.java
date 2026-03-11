package com.mosquito.mosquitowiki.product.domain;

import com.mosquito.mosquitowiki.product.dto.TagRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tags")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tagType;

    @Column(nullable = false, unique = true)
    private String tagValue;

    private String color;

    public static Tag create(TagRequest request) {
        return Tag.builder()
                .tagType(request.getTagType())
                .tagValue(request.getTagValue())
                .color(request.getColor())
                .build();
    }
}
