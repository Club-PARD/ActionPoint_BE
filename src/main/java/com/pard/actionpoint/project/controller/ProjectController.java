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
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project")

public class ProjectController {
    private final ProjectService projectService;

    // 프로젝트 생성
    @PostMapping
    @Operation(summary = "[프로젝트 생성]",
            description = "프로젝트를 새로 생성합니다.\n" +
                    "Req : 유저 ID (Header 'X-USER-ID'), 프로젝트 이름\n" +
                    "Res : 프로젝트 코드")
    public ResponseEntity<?> createProject(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestBody ProjectDto.ProjectCreateDto projectDto
    ) {
        String projectCode = projectService.createProject(userId, projectDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("projectCode", projectCode));
    }

    // 프로젝트 참여
    @PostMapping("/join")
    @Operation(summary = "[프로젝트 참여]",
            description = "유저가 처음 프로젝트를 참여합니다.\n" +
                    "Req : 유저 ID (Header 'X-USER-ID), 프로젝트 코드\n" +
                    "Res : ")
    public ResponseEntity<?> joinProject(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestBody ProjectDto.ProjectJoinDto projectDto
    ) {
        projectService.joinProject(userId, projectDto);
        return ResponseEntity.ok().body("Project joined");
    }

    // 유저가 프로젝트 나갈 때
    @PostMapping("/{projectId}/leave")
    @Operation(summary = "[프로젝트 나가기]",
            description = "유저가 프로젝트를 나가며 프로젝트가 아무도 없는 경우 프로젝트가 삭제됩니다.\n" +
                    "Req : 유저 ID (Header 'X-USER-ID'), 프로젝트 ID (URL Path)\n" +
                    "Res : ")
    public ResponseEntity<?> leaveProject(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable Long projectId
    ) {
        projectService.leaveProject(userId, projectId);
        return ResponseEntity.ok().body("Project leaved");
    }

    // 프로젝트 내부 페이지
    @GetMapping("/{projectId}/details")
    @Operation(summary = "[프로젝트 내부 페이지]",
            description = "프로젝트를 눌렀을 경우 즉, 회의록 리스트를 보고 싶을 때 사용합니다. 회의록 리스트는 최신순으로 드립니다.\n" +
                    "Req : 유저 ID (Header 'X-USER-ID'), 프로젝트 ID (URL Path)\n" +
                    "Res : 프로젝트 이름, 프로젝트 코드, <List> {회의 ID, 회의 제목, 회의 날짜, <List> {액션포인트 내용, 유저 ID, 완료 여부}}")
    public ResponseEntity<ProjectDetailDto> getProjectDetails(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable Long projectId
    ) {
        ProjectDetailDto projectDetailDto = projectService.getProjectDetails(userId, projectId);
        return ResponseEntity.ok().body(projectDetailDto);
    }

    // 프로젝트 리스트 페이지
    @GetMapping("/lists")
    @Operation(summary = "[프로젝트 리스트 페이지]",
            description = "유저가 속해있는 프로젝트 목록을 조회하도록 합니다.\n" +
                    "Status - 0: 정상, 1: 회의 X, 2: 회의 한 달전\n" +
                    "Req : 유저 ID (Header 'X-USER-ID')\n" +
                    "Res : <List> {프로젝트 ID, 프로젝트 이름, 생성자 ID, 참여한 유저 수 - 1, 프로젝트 상태}")
    public ResponseEntity<List<ProjectDto.ProjectListDto>> getProjects(
            @RequestHeader("X-USER-ID") Long userId) {
        List<ProjectDto.ProjectListDto> projectList = projectService.getUserProjects(userId);
        return ResponseEntity.ok().body(projectList);
    }
}
