package com.mosquito.mosquitowiki.product.domain;


import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "categories")
@Getter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String slug;
}
