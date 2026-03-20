package com.mosquito.mosquitowiki.swatch;

import com.mosquito.mosquitowiki.product.domain.Product;
import com.mosquito.mosquitowiki.swatch.dto.SwatchCreateRequest;
import com.mosquito.mosquitowiki.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "swatches")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Swatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(columnDefinition = "TEXT")
    private String content;

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

    public static Swatch create(SwatchCreateRequest request, List<String> imageUrls, User user) {
        return Swatch.builder()
                .user(user)
                .content(request.getContent())
                .sourceType(request.getSourceType())
                .tweetUrl(request.getTweetUrl())
                .imageUrls(imageUrls)
                .likeCount(0)
                .viewCount(0)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
