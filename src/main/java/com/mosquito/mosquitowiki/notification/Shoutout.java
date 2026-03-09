package com.mosquito.mosquitowiki.notification;

import com.mosquito.mosquitowiki.product.domain.Product;
import com.mosquito.mosquitowiki.users.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "shoutouts")
public class Shoutout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(columnDefinition = "TEXT")
    private String linkUrl;

    @Column(nullable = false)
    private Boolean isActive = true;

    private LocalDateTime expiresAt;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
