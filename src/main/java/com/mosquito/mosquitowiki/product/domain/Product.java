package com.mosquito.mosquitowiki.product.domain;

import com.mosquito.mosquitowiki.product.dto.ProductModifyRequest;
import com.mosquito.mosquitowiki.users.User;
import com.mosquito.mosquitowiki.utils.SlugUtil;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Product parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(length = 300)
    private String nameKo;

    @Column(length = 200)
    private String optionName;

    @Column(length = 300)
    private String optionNameKo;

    @Column(length = 200)
    private String fullName;

    @Column(length = 200)
    private String fullNameKo;

    @Column(length = 300, unique = true)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String officialImageUrl;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public void update(ProductModifyRequest request, String image) {
        if (request.getName() != null) {
            this.name = request.getName();
            this.slug = SlugUtil.toSlug(request.getName());
        }
        if (request.getNameKo() != null) this.nameKo = request.getNameKo();
        if (request.getDescription() != null) this.description = request.getDescription();
        if (image != null) this.officialImageUrl = image;
    }
}
