package com.mosquito.mosquitowiki.product.domain;

import com.mosquito.mosquitowiki.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "product_likes")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public static ProductLike create(Product product, User user) {
        return ProductLike.builder()
                .product(product)
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
