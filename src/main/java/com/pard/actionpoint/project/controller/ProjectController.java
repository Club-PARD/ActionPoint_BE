package com.pard.actionpoint.project.controller;

import com.pard.actionpoint.DTO.ProjectDetailDto;
import com.pard.actionpoint.DTO.ProjectDto;
import com.pard.actionpoint.project.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project")

public class ProjectController {
    private final ProjectService projectService;

    // 프로젝트 생성
    @PostMapping
    @Operation(summary = "프로젝트 생성")
    public ResponseEntity<?> createProject(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestBody ProjectDto.ProjectCreateDto projectDto
    ) {
        projectService.createProject(userId, projectDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 프로젝트 참여
    @PostMapping("/join")
    @Operation(summary = "프로젝트 참여")
    public ResponseEntity<?> joinProject(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestBody ProjectDto.ProjectJoinDto projectDto
    ) {
        projectService.joinProject(userId, projectDto);
        return ResponseEntity.ok().body("Project joined");
    }

    // 유저가 프로젝트 나갈 때
    @PostMapping("/{projectId}/leave")
    @Operation(summary = "프로젝트 나가기")
    public ResponseEntity<?> leaveProject(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable Long projectId
    ) {
        projectService.leaveProject(userId, projectId);
        return ResponseEntity.ok().body("Project leaved");
    }

    // 프로젝트 내부 페이지
    @GetMapping("/{projectId}/details")
    @Operation(summary = "프로젝트 내부 페이지")
    public ResponseEntity<ProjectDetailDto> getProjectDetails(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable Long projectId
    ) {
        ProjectDetailDto projectDetailDto = projectService.getProjectDetails(userId, projectId);
        return ResponseEntity.ok().body(projectDetailDto);
    }

    // 프로젝트 리스트 페이지
    @GetMapping("/lists")
    @Operation(summary = "프로젝트 리스트 페이지", description = "Status - 0: 정상, 1: 회의 X, 2: 회의 한 달전")
    public ResponseEntity<List<ProjectDto.ProjectListDto>> getProjects(
            @RequestHeader("X-USER-ID") Long userId) {
        List<ProjectDto.ProjectListDto> projectList = projectService.getUserProjects(userId);
        return ResponseEntity.ok().body(projectList);
    }
}
