package com.mosquito.mosquitowiki.swatch;

import jakarta.persistence.*;

@Entity
@Table(name = "swatch_tags")
public class SwatchTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "swatch_id")
    private Swatch swatch;

    @Column(length = 30)
    private String tagType;

    @Column(length = 50)
    private String tagValue;
}
