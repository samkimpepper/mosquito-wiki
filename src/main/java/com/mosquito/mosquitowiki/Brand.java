package com.mosquito.mosquitowiki;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "brands")
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
}
