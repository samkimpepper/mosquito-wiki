package com.mosquito.mosquitowiki.product.domain;

import com.mosquito.mosquitowiki.product.dto.BrandModifyRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "brands")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String nameKo;

    @Column(unique = true)
    private String slug;

    private String logoUrl;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public void update(BrandModifyRequest request) {
        if (request.getBrandName() != null)
            this.name = request.getBrandName();
        if (request.getBrandNameKo() != null)
            this.nameKo = request.getBrandNameKo();

    }

    public void updateImage(String newImageUrl) {
        this.logoUrl = newImageUrl;
    }
}
