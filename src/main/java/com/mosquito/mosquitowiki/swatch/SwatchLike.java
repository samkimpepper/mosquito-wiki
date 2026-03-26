package com.mosquito.mosquitowiki.swatch;

import com.mosquito.mosquitowiki.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "swatch_likes")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SwatchLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "swatch_id")
    private Swatch swatch;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public static SwatchLike create(User user, Swatch swatch) {
        return SwatchLike.builder()
                .user(user)
                .swatch(swatch)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
