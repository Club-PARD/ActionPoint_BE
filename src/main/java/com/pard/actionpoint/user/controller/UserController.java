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
    @Operation(summary = "[유저 정보 보기 (현재 접속용)]",
            description = "현재 접속 중인 사용자의 정보를 반환합니다. 향후 프로필 기능이 나올 때 사용합니다.<br>" +
                    "Req : <br>" +
                    "Res : 유저 ID, 유저 이름, 유저 이메일")
    public ResponseEntity<UserDto.UserResDto> getUserProfile(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(userService.read(user.getId()));
    }

    @GetMapping("/dashboard")
    @Operation(summary = "[메인 대시보드 페이지]",
            description = "로그인 이후 화면인 메인 대시보드 페이지에 대한 정보를 모두 전달합니다.<br>" +
                    "Req : 유저 ID (Header 'X-USER-ID')<br>" +
                    "Res : <List> {프로젝트 ID, 프로젝트 이름, 최신 회의 ID, 최신 회의 이름, 추가 논의, <List> {액션포인트 내용, 유저 ID, 완료 여부}, 액션 포인트 개수}")
    public ResponseEntity<DashboardDto> getDashboard(
            @RequestHeader("X-USER-ID") Long userId) {
        DashboardDto dto = userService.getDashboard(userId);
        return ResponseEntity.ok(dto);
    }
}
