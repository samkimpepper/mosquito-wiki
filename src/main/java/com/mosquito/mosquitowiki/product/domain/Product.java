package com.mosquito.mosquitowiki.product.domain;

import com.mosquito.mosquitowiki.product.dto.ProductModifyRequest;
import com.mosquito.mosquitowiki.users.User;
import com.mosquito.mosquitowiki.utils.SlugUtil;
import com.mosquito.mosquitowiki.utils.StringListConverter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modified_by")
    private User modifiedBy;

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

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<String> officialImageUrls = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime modifiedAt;

    public void updateImage(List<String> images) {
        this.officialImageUrls = images;
    }

    public String getOfficialImageUrl() {
        return this.officialImageUrls != null ? officialImageUrls.get(0) : null;
    }

    public void update(ProductModifyRequest request, User user) {
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

        this.modifiedBy = user;
        this.modifiedAt = LocalDateTime.now();
    }

    private void slugUpdate() {
        this.slug = SlugUtil.toSlug(this.brand.getNameKo() + " " + this.nameKo + " " + this.optionNameKo);
    }
}
