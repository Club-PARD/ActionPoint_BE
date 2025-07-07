package com.pard.actionpoint.user.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.pard.actionpoint.user.domain.Role;
import com.pard.actionpoint.user.domain.User;
import com.pard.actionpoint.user.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GoogleTokenService {
    private final UserRepo userRepo;

    public Long processGoogleToken(String idTokenString) {
        // 구글 ID 토큰 검증
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier
                .Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(List.of("GOOGLE_CLIENT_ID")) // 실제 클라이언트 ID
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
        User user = userRepo.findByUserEmail(email)
                .orElseGet(() -> userRepo.save(User.builder()
                        .userEmail(email)
                        .userName(name)
                        .socialId(sub)
                        .role(Role.USER)
                        .build()));

        return user.getId(); // Auto Increment
    }
}
