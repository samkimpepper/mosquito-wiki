package com.mosquito.mosquitowiki.swatch;

import com.mosquito.mosquitowiki.users.User;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "combos")
public class Combo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 300)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ComboType comboType;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private SourceType sourceType;

    @Column(columnDefinition = "TEXT")
    private String tweetUrl;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<String> imageUrls = new ArrayList<>();

    @Column(nullable = false)
    private Integer likeCount = 0;

    @Column(nullable = false)
    private Integer viewCount = 0;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
