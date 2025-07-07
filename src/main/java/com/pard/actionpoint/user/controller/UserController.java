package com.pard.actionpoint.user.controller;

import com.pard.actionpoint.DTO.DashboardDto;
import com.pard.actionpoint.DTO.UserDto;
import com.pard.actionpoint.user.domain.User;
import com.pard.actionpoint.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")

public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    @Operation(summary = "유저 정보 보기 (한 사람용)")
    public ResponseEntity<UserDto.UserResDto> getUserProfile(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(userService.read(user.getId()));
    }

    @GetMapping("/dashboard")
    @Operation(summary = "메인 대시보드 페이지 이동")
    public ResponseEntity<DashboardDto> getDashboard(
            @RequestHeader("X-USER-ID") Long userId) {
        DashboardDto dto = userService.getDashboard(userId);
        return ResponseEntity.ok(dto);
    }
}
