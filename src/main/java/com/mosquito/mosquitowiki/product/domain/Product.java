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

    @Column(length = 300, unique = true, nullable = false)
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

    public void updateName(ProductModifyRequest request, User user) {
        if (request.getName() != null) {
            this.name = request.getName();
            this.fullName = this.brand.getName() + " " + this.name;
        }

        if (request.getNameKo() != null) {
            this.nameKo = request.getNameKo();
            this.fullNameKo = this.brand.getNameKo() + " " + this.nameKo;
        }

        this.modifiedBy = user;
        this.modifiedAt = LocalDateTime.now();
    }

    public void updateOptionName(Product parent, ProductModifyRequest request, User user) {
        if (request.getOption() != null) {
            this.optionName = request.getOption();
            this.fullName = parent.getFullName() + " " + this.optionName;
        }
        if (request.getOptionKo() != null) {
            this.optionNameKo = request.getOptionKo();
            this.fullNameKo = parent.getFullNameKo() + " " + this.optionNameKo;
        }
        if (request.getDescription() != null) this.description = request.getDescription();

        this.modifiedBy = user;
        this.modifiedAt = LocalDateTime.now();
    }
}
