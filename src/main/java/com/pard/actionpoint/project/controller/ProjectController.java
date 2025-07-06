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

    // 프로젝트 생성
    @PostMapping
    public ResponseEntity<?> createProject(
            @RequestHeader("X-USER_ID") Long userId,
            @RequestBody ProjectDto.ProjectCreateDto projectDto
    ) {
        projectService.createProject(userId, projectDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 프로젝트 참여
    @PostMapping("/join")
    public ResponseEntity<?> joinProject(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestBody ProjectDto.ProjectJoinDto projectDto
    ) {
        projectService.joinProject(userId, projectDto);
        return ResponseEntity.ok().body("Project joined");
    }

    // 유저가 프로젝트 나갈 때
    @PostMapping("/{projectId}/leave")
    public ResponseEntity<?> leaveProject(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable Long projectId
    ) {
        projectService.leaveProject(userId, projectId);
        return ResponseEntity.ok().body("Project leaved");
    }
}
