package com.pard.actionpoint.project.service;

import com.pard.actionpoint.DTO.ProjectDto;
import com.pard.actionpoint.project.domain.Project;
import com.pard.actionpoint.project.repo.ProjectRepo;
import com.pard.actionpoint.user.domain.User;
import com.pard.actionpoint.user.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor

public class ProjectService {
    private final UserRepo userRepo;
    private final ProjectRepo projectRepo;

    // 새 프로젝트 생성
    public Project createProject(String projectName, User owner, ProjectRepo projectRepo) {
        String code = generateUniqueCode(projectRepo);

        Project project = Project.builder()
                .projectName(projectName)
                .projectCode(code)
                .ownerId(owner.getId())
                .projectUserCnt(1)
                .status(1) // 프로젝트는 있지만 회의는 없는 상태
                .build();

        return projectRepo.save(project);
    }

    // 프로젝트 코드 생성
    private static String generateUniqueCode(ProjectRepo projectRepo) {
        String code;
        do {
            code = randomCode();
        } while(projectRepo.existsByProjectCode(code));
        return code;
    }
    private static String randomCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < 6; i++) {
            code.append(chars.charAt(random.nextInt(chars.length())));
        }
        return code.toString();
    }
//    private final MeetingRepo meetingRepo;
//
//    public void updateAllProjectStatuses() {
//        List<Project> projects = projectRepo.findAll();
//
//        for (Project project : projects) {
//            List<Meeting> meetings = meetingRepo.findByProjectId(project.getId());
//
//            if (meetings.isEmpty()) {
//                project.setStatus(1); // 회의 없음
//            } else {
//                Meeting latest = meetings.stream()
//                        .max(Comparator.comparing(Meeting::getMeetingDate))
//                        .orElse(null);
//
//                if (latest != null && latest.getMeetingDate().isBefore(LocalDate.now().minusMonths(1))) {
//                    project.setStatus(2); // 한 달 전
//                } else {
//                    project.setStatus(0); // 정상
//                }
//            }
//
//            project.save(project);
//        }
//    }
}
