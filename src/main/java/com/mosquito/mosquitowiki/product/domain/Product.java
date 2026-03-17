package com.mosquito.mosquitowiki.product.domain;

import com.mosquito.mosquitowiki.product.dto.ProductModifyRequest;
import com.mosquito.mosquitowiki.users.User;
import com.mosquito.mosquitowiki.utils.SlugUtil;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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

    @Column(length = 200)
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

    @Column(columnDefinition = "TEXT")
    private String officialImageUrl2;

    @Column(columnDefinition = "TEXT")
    private String officialImageUrl3;

    @Column(columnDefinition = "TEXT")
    private String officialImageUrl4;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public void update(ProductModifyRequest request, List<String> images) {
        if (request.getName() != null) {
            this.name = request.getName();
        }
        if (request.getOption() != null) {
            this.optionName = request.getOption();
        }
        if (request.getNameKo() != null) {
            this.nameKo = request.getNameKo();
            slugUpdate();
        }
        if (request.getOptionKo() != null) {
            this.optionNameKo = request.getOptionKo();
            slugUpdate();
        }
        if (request.getDescription() != null) this.description = request.getDescription();
        if (images.size() > 0) this.officialImageUrl = images.get(0);
        if (images.size() > 1) this.officialImageUrl2 = images.get(1);
        if (images.size() > 2) this.officialImageUrl3 = images.get(2);
        if (images.size() > 3) this.officialImageUrl4 = images.get(3);
    }

    public void updateImageUrl1(String imageUrl) {
        this.officialImageUrl = imageUrl;
    }

    public void updateImageUrl2(String imageUrl) {
        this.officialImageUrl2 = imageUrl;
    }

    public void updateImageUrl3(String imageUrl) {
        this.officialImageUrl3 = imageUrl;
    }

    public void updateImageUrl4(String imageUrl) {
        this.officialImageUrl4 = imageUrl;
    }

    private void slugUpdate() {
        this.slug = SlugUtil.toSlug(this.brand.getNameKo() + " " + this.nameKo + " " + this.optionNameKo);
    }
}
