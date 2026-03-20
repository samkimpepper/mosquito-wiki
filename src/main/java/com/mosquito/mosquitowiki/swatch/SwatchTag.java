package com.mosquito.mosquitowiki.swatch;

import com.mosquito.mosquitowiki.product.domain.Tag;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "swatch_tags")
@Getter
public class SwatchTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "swatch_id")
    private Swatch swatch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;
}
