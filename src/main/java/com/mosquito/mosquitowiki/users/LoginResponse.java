package com.mosquito.mosquitowiki.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
    private Long id;
    private String email;
    private String name;
    private String profileImageUrl;
}
