package com.mosquito.mosquitowiki.swatch;

import com.mosquito.mosquitowiki.product.domain.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "swatch_links")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SwatchLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "swatch_id")
    private Swatch swatch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer displayOrder;


}
