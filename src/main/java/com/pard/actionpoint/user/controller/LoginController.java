package com.pard.actionpoint.user.controller;

import com.pard.actionpoint.user.service.GoogleTokenService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "[로그인]",
            description = "로그인 처리에 대한 요청입니다.\n" +
                    "구글 로그인을 하게 되면 해당 유저의 정보를 저장하고 ID를 생성합니다.")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String idToken = body.get("idToken");
        Long userId = googleTokenService.processGoogleToken(idToken);
        return ResponseEntity.ok(Map.of("userId", userId));
    }
}
