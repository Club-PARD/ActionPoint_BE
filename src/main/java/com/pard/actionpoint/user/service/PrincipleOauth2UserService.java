package com.pard.actionpoint.user.service;

import com.pard.actionpoint.user.domain.User;
import com.pard.actionpoint.user.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor

public class PrincipleOauth2UserService extends DefaultOAuth2UserService {
    private final UserRepo userRepo;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException{
        log.info("User Req from Google : " + oAuth2UserRequest);
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        log.info("User Req from OAuth : " + oAuth2User);

        String userEmail = (String) oAuth2User.getAttributes().get("email");
        String userName = (String) oAuth2User.getAttributes().get("name");
        String socialId = (String) oAuth2User.getAttributes().get("sub");

        userRepo.findByUserEmail(userEmail)
                .orElseGet(() -> userRepo.save(
                        User.builder()
                                .userEmail(userEmail)
                                .userName(userName)
                                .socialId(socialId)
                                .role(com.pard.actionpoint.user.domain.Role.USER)
                                .build()
                ));

        return oAuth2User;

    }
}
