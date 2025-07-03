package com.pard.actionpoint.user.controller;

import com.pard.actionpoint.DTO.UserDto;
import com.pard.actionpoint.user.domain.User;
import com.pard.actionpoint.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")

public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserDto.UserResDto> getUserProfile(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(userService.read(user.getId()));
    }
}
