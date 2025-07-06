package com.pard.actionpoint.project.controller;

import com.pard.actionpoint.DTO.ProjectDto;
import com.pard.actionpoint.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project")

public class ProjectController {
    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<?> createProject(
            @RequestHeader("X-USER_ID") Long userId,
            @RequestBody ProjectDto.ProjectCreateDto projectDto
    ) {
        projectService.createProject(userId, projectDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{projectId}/leave")
    public ResponseEntity<?> leaveProject(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable Long projectId
    ) {
        projectService.leaveProject(userId, projectId);
        return ResponseEntity.ok().body("Project leaved");
    }
}
