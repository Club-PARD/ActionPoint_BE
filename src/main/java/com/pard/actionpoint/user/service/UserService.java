package com.pard.actionpoint.user.service;

import com.pard.actionpoint.DTO.UserDto;
import com.pard.actionpoint.user.domain.User;
import com.pard.actionpoint.user.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class UserService {
    private final UserRepo userRepo;

    public UserDto.UserResDto read(Long userId){
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return UserDto.UserResDto.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .userEmail(user.getUserEmail())
                .build();
    }

    public void delete(Long userId){
        userRepo.deleteById(userId);
    }
}
