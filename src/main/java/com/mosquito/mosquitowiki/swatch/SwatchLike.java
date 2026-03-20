package com.mosquito.mosquitowiki.swatch;

import com.mosquito.mosquitowiki.product.domain.Product;
import com.mosquito.mosquitowiki.users.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "swatch_likes")
@Getter
public class SwatchLike {
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
}
