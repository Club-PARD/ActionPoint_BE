package com.pard.actionpoint.user.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.pard.actionpoint.common.mailService.MailService;
import com.pard.actionpoint.user.domain.Role;
import com.pard.actionpoint.user.domain.User;
import com.pard.actionpoint.user.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GoogleTokenService {
    private final UserRepo userRepo;
    private final MailService mailService;

    public Long processGoogleToken(String idTokenString) {
        // 구글 ID 토큰 검증
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier
                .Builder(new NetHttpTransport(), new JacksonFactory())
//                .setAudience(List.of("GOOGLE_CLIENT_ID")) // 실제 클라이언트 ID
                .setAudience(List.of("226608210003-oiuob1n5qioht801eac5kru523j46la1.apps.googleusercontent.com"))
                .build();

        GoogleIdToken idToken;
        try {
            idToken = verifier.verify(idTokenString);
        } catch (Exception e) {
            throw new RuntimeException("Invalid token");
        }

        GoogleIdToken.Payload payload = idToken.getPayload();
        String email = payload.getEmail();
        String name = (String) payload.get("name");
        String sub = payload.getSubject();

        // 유저가 없으면 저장
        Optional<User> existingUser = userRepo.findByUserEmail(email);

        User user = existingUser.orElseGet(() -> {
            mailService.sendWelcomeEmail(email, name); // 신규일 때만 전송
            return userRepo.save(User.builder()
                    .userEmail(email)
                    .userName(name)
                    .socialId(sub)
                    .role(Role.USER)
                    .build());
        });

        return user.getId(); // Auto Increment
    }
}
