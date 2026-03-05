package com.mosquito.mosquitowiki.auth;

import com.mosquito.mosquitowiki.users.AuthUser;
import com.mosquito.mosquitowiki.users.User;
import com.mosquito.mosquitowiki.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId();

        String email;
        String name;
        String picture;
        String providerId;

        if (provider.equals("kakao")) {
            Map<String, Object> kakaoAccount = oAuth2User.getAttribute("kakao_account");
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

            providerId = String.valueOf(oAuth2User.getAttribute("id"));
            email = (String) kakaoAccount.get("email");
            name = (String) profile.get("name");
            picture = profile != null ? (String) profile.get("profile_image_url") : null;
        } else {
            providerId = oAuth2User.getAttribute("sub");
            email = oAuth2User.getAttribute("email");
            name = oAuth2User.getAttribute("name");
            picture = oAuth2User.getAttribute("picture");
        }

        User user = userRepository.findByProviderAndProviderId("google", providerId)
                .orElseGet(() -> userRepository.save(
                        User.builder()
                                .email(email)
                                .name(name)
                                .profileImageUrl(picture)
                                .provider(provider)
                                .providerId(providerId)
                                .createdAt(LocalDateTime.now())
                                .build()
                ));

        return new AuthUser(user, oAuth2User.getAttributes());
    }
}
