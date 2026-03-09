package com.mosquito.mosquitowiki.swatch;

import com.mosquito.mosquitowiki.product.domain.Product;
import jakarta.persistence.*;

@Entity
@Table(name = "combo_items")
public class ComboItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "combo_id")
    private Combo combo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(length = 50)
    private String role;

    @Column
    private Integer displayOrder;
}
