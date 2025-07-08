package com.pard.actionpoint.project.service;

import com.pard.actionpoint.DTO.ProjectDetailDto;
import com.pard.actionpoint.DTO.ProjectDto;
import com.pard.actionpoint.actionPoint.domain.ActionPoint;
import com.pard.actionpoint.actionPoint.repo.ActionPointRepo;
import com.pard.actionpoint.global.exception.BadRequestException;
import com.pard.actionpoint.meeting.domain.Meeting;
import com.pard.actionpoint.meeting.repo.MeetingRepo;
import com.pard.actionpoint.project.domain.Project;
import com.pard.actionpoint.project.repo.ProjectRepo;
import com.pard.actionpoint.user.domain.User;
import com.pard.actionpoint.user.repo.UserRepo;
import com.pard.actionpoint.userProject.domain.UserProject;
import com.pard.actionpoint.userProject.repo.UserProjectRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class ProjectService {
    private final UserRepo userRepo;
    private final ProjectRepo projectRepo;
    private final UserProjectRepo userProjectRepo;
    private final MeetingRepo meetingRepo;
    private final ActionPointRepo actionPointRepo;

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int CODE_LENGTH = 5;
    private final SecureRandom random = new SecureRandom();

    // 프로젝트 생성
    public String createProject(Long userId, ProjectDto.ProjectCreateDto projectCreateDto) {
        String projectCode = generateUniqueCode();
        Project project = new Project(
                projectCreateDto.getProjectName(),
                projectCode,
                userId,
                1,
                1
        ); // 기본 상태는 회의가 없는 프로젝트 상태
        projectRepo.save(project);
        projectRepo.flush();

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new BadRequestException("User not found"));

        UserProject userProject = new UserProject();
        userProject.setUser(user);
        userProject.setProject(project);
        userProjectRepo.save(userProject);

        return projectCode;
    }

    private String generateUniqueCode() {
        String code;
        do {
            code = randomCode();
        } while(projectRepo.existsByProjectCode(code));

        return code;
    }

    private String randomCode() {
        StringBuilder code = new StringBuilder(CODE_LENGTH);
        for(int i = 0; i < CODE_LENGTH; i++) {
            code.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }

        return code.toString();
    }

    // 유저가 프로젝트를 참여하는 경우
    @Transactional
    public void joinProject(Long userId, ProjectDto.ProjectJoinDto projectJoinDto) {
        // 유저, 프로젝트 조회
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new BadRequestException("User not found"));

        Project project = projectRepo.findByProjectCode(projectJoinDto.getProjectCode())
                .orElseThrow(() -> new BadRequestException("Project not found"));

        // 기존 참여 여부 확인
        if(userProjectRepo.existsByUserAndProject(user, project)){
            throw new BadRequestException("Project already exists");
        }

        // 관계 저장
        UserProject userProject = new UserProject();
        userProject.setUser(user);
        userProject.setProject(project);
        userProjectRepo.save(userProject);

        project.setProjectUserCnt(project.getProjectUserCnt() + 1);
        projectRepo.save(project);
    }

    // 유저가 프로젝트를 나가는 경우
    @Transactional
    public void leaveProject(Long userId, Long projectId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new BadRequestException("User not found"));

        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new BadRequestException("Project not found"));

        // UserProject 관계 삭제
        UserProject userProject = userProjectRepo.findByUserAndProject(user, project)
                .orElseThrow(() -> new BadRequestException("User is not in this project"));
        userProjectRepo.delete(userProject);

        // 인원 수 감소
        int updatedCount = project.getProjectUserCnt() - 1;
        project.setProjectUserCnt(updatedCount);

        // 0명이면 프로젝트 삭제
        if (updatedCount == 0) {
            projectRepo.delete(project);
        } else {
            projectRepo.save(project);
        }
    }

    // 프로젝트 내부 페이지
    public ProjectDetailDto getProjectDetails(Long userId, Long projectId){
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new BadRequestException("Project not found"));

        // 최신순으로 회의록 제공
        List<Meeting> meetings = meetingRepo.findAllByProjectIdOrderByMeetingDateDesc(projectId);

        // 회의 내용
        List<ProjectDetailDto.MeetingListDto> meetingDtos = meetings.stream().map(meeting -> {
            List<ActionPoint> userActionPoints = actionPointRepo.findByMeetingIdAndUserId(meeting.getId(), userId);
            // 회의 별 액션포인트 내용
            List<ProjectDetailDto.ActionPointDto> actionPointDtos = userActionPoints.stream().map(ap ->
                    new ProjectDetailDto.ActionPointDto(ap.getId(), ap.getActionContent(), ap.getIsFinished())
            ).toList();

            return new ProjectDetailDto.MeetingListDto(
                    meeting.getId(),
                    meeting.getMeetingTitle(),
                    meeting.getMeetingDate(),
                    actionPointDtos
            );
        }).toList();

        return new ProjectDetailDto(project.getProjectName(), project.getProjectCode(), meetingDtos);
    }

    // 프로젝트 리스트 페이지
    @Transactional
    public List<ProjectDto.ProjectListDto> getUserProjects(Long userId) {
        List<Project> projects = projectRepo.findProjectsByUserIdOrderByCreatedAtDesc(userId);

        return projects.stream().map(project -> {
            String ownerName = userRepo.findById(project.getOwnerId())
                    .map(User::getUserName)
                    .orElse("Unknown");

            return new ProjectDto.ProjectListDto(
                    project.getId(),
                    project.getProjectName(),
                    ownerName,
                    project.getProjectUserCnt() - 1, // 본인 제외
                    project.getProjectStatus()
            );
        }).collect(Collectors.toList());
    }
}
