package com.mosquito.mosquitowiki.swatch;

import com.mosquito.mosquitowiki.product.Product;
import jakarta.persistence.*;

@Entity
@Table(name = "comparison_links")
public class ComparisonLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comparison_swatch_id")
    private ComparisonSwatch comparisonSwatch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer displayOrder;
}
