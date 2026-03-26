package com.mosquito.mosquitowiki.users;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserThumbnailResponse {
    private String name;
    private String profileImageUrl;

    public static UserThumbnailResponse from(User user) {
        return UserThumbnailResponse.builder()
                .name(user.getName())
                .profileImageUrl(user.getProfileImageUrl())
                .build();
    }
}
