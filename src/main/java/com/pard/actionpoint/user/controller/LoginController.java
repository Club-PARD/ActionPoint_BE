package com.pard.actionpoint.user.controller;

import com.pard.actionpoint.user.service.GoogleTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class LoginController {

    private final GoogleTokenService googleTokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String idToken = body.get("idToken");
        Long userId = googleTokenService.processGoogleToken(idToken);
        return ResponseEntity.ok(Map.of("userId", userId));
    }
}
