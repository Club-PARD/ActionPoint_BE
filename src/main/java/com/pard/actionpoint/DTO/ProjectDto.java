package com.pard.actionpoint.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

public class ProjectDto {
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class ProjectCreateDto {
        private String projectName;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class ProjectJoinDto {
        private String projectCode;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class ProjectListDto {
        private Long projectId;
        private String projectName;
        private String ownerName;
        private int userCnt;
        private int projectStatus; // 0: 정상, 1: 회의 X, 2: 1달전 회의
    }
}
