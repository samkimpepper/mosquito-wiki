package com.mosquito.mosquitowiki.users;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {

    @GetMapping("/me")
    public ResponseEntity<LoginResponse> me(@AuthenticationPrincipal AuthUser user) {
        Long userId = user.getUser().getId();
        String email = user.getUser().getEmail();
        String name = user.getUser().getName();
        String profileImageUrl = user.getUser().getProfileImageUrl();

        return ResponseEntity.ok(new LoginResponse(
                userId,
                email,
                name,
                profileImageUrl
        ));
    }
}
