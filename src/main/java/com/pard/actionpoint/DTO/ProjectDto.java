package com.pard.actionpoint.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ProjectDto {
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class ProjectCreateDto {
        private String projectName;
    }
}
