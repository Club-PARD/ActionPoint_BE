package com.pard.actionpoint.project.service;

import com.pard.actionpoint.DTO.ProjectDto;
import com.pard.actionpoint.global.exception.BadRequestException;
import com.pard.actionpoint.project.domain.Project;
import com.pard.actionpoint.project.repo.ProjectRepo;
import com.pard.actionpoint.user.domain.User;
import com.pard.actionpoint.user.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor

public class ProjectService {
    private final UserRepo userRepo;
    private final ProjectRepo projectRepo;

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int CODE_LENGTH = 5;
    private final SecureRandom random = new SecureRandom();

    // 프로젝트 생성
    public void createProject(Long userId, ProjectDto.ProjectCreateDto projectCreateDto) {
        String projectCode = generateUniqueCode();
        Project project = new Project(
                projectCreateDto.getProjectName(),
                projectCode,
                userId,
                1,
                1
        );
        projectRepo.save(project);
    }

    private String generateUniqueCode() {
        String code;
        do {
            code = randomCode();
        } while(!projectRepo.existsByProjectCode(code));

        return code;
    }

    private String randomCode() {
        StringBuilder code = new StringBuilder(CODE_LENGTH);
        for(int i = 0; i < CODE_LENGTH; i++) {
            code.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }

        return code.toString();
    }

    // 유저가 프로젝트를 나가는 경우
    @Transactional
    public void leaveProject(Long userId, Long projectId) {
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new BadRequestException("Project not found"));

        int updatedCount = project.getProjectUserCnt() - 1;
        project.setProjectUserCnt(updatedCount);

        if (updatedCount == 0) {
            projectRepo.delete(project);
        } else {
            projectRepo.save(project);
        }
    }

}
