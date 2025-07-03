package com.pard.actionpoint.DTO;

import lombok.*;

public class UserDto {
    @Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
    public static class UserResDto {
        private Long id;
        private String userName;
        private String userEmail;
    }
}
