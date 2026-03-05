package com.mosquito.mosquitowiki.users;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users",
    uniqueConstraints = @UniqueConstraint(columnNames = {"provider", "provider_id"}))
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(length = 20)
    private String provider;

    @Column(length = 200)
    private String providerId;

    private String profileImageUrl;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
