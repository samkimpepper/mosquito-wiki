package com.mosquito.mosquitowiki.product.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_tags")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    public static ProductTag create(Product product, Tag tag) {
        return ProductTag.builder()
                .product(product)
                .tag(tag)
                .build();
    }
}
