package com.pard.actionpoint.common.config;

import com.pard.actionpoint.user.service.PrincipleOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor

public class SecurityConfig {
    private final PrincipleOauth2UserService principalOauth2UserService;

    private final CorsConfig corsConfig;

    @Bean
    public SecurityFilterChain FilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        http.addFilter(corsConfig.corsFilter());

        http.authorizeHttpRequests(au -> au.anyRequest().permitAll());

        http.oauth2Login(
                oauth -> oauth
                        .loginPage("/login") // 프론트 : 로그인 화면
                        .defaultSuccessUrl("/loginSuccess") // 프론트 : 로그인 성공 후
                        .userInfoEndpoint(
                                userInfo -> userInfo.userService(principalOauth2UserService)
                        )
        );

        return http.build();
    }
}
