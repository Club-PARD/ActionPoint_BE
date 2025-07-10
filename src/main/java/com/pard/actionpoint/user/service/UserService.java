package com.pard.actionpoint.user.service;

import com.pard.actionpoint.DTO.DashboardDto;
import com.pard.actionpoint.DTO.ProjectDetailDto;
import com.pard.actionpoint.DTO.UserDto;
import com.pard.actionpoint.actionPoint.domain.ActionPoint;
import com.pard.actionpoint.actionPoint.repo.ActionPointRepo;
import com.pard.actionpoint.meeting.domain.Meeting;
import com.pard.actionpoint.meeting.repo.MeetingRepo;
import com.pard.actionpoint.project.domain.Project;
import com.pard.actionpoint.project.repo.ProjectRepo;
import com.pard.actionpoint.user.domain.User;
import com.pard.actionpoint.user.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class UserService {
    private final UserRepo userRepo;
    private final ProjectRepo projectRepo;
    private final MeetingRepo meetingRepo;
    private final ActionPointRepo actionPointRepo;

    public UserDto.UserResDto read(Long userId){
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return UserDto.UserResDto.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .userEmail(user.getUserEmail())
                .build();
    }

    public void delete(Long userId){
        userRepo.deleteById(userId);
    }

    // 대시보드 페이지 이동
    @Transactional
    public DashboardDto getDashboard(Long userId) {
        List<Project> projects = projectRepo.findProjectsByUserId(userId);
        if (projects == null) projects = Collections.emptyList();

        List<DashboardDto.ProjectInfoDto> projectInfoDtos = new ArrayList<>();

        for (Project project : projects) {
            if (project == null) continue;

            DashboardDto.ProjectInfoDto projectInfo = new DashboardDto.ProjectInfoDto();
            projectInfo.setProjectId(project.getId());
            projectInfo.setProjectName(project.getProjectName());

            // 가장 최근 회의
            List<Meeting> recentMeetings = meetingRepo.findRecentMeetingByProjectId(
                    project.getId(),
                    PageRequest.of(0, 1)
            );
            if (recentMeetings == null) recentMeetings = Collections.emptyList();

            if (!recentMeetings.isEmpty()) {
                Meeting latestMeeting = recentMeetings.get(0);
                if (latestMeeting != null) {
                    projectInfo.setLatestMeetingId(latestMeeting.getId());

                    // 유저의 액션 포인트
                    List<ActionPoint> actionPoints = actionPointRepo.findByUserIdAndMeetingId(userId, latestMeeting.getId());
                    if (actionPoints == null) actionPoints = Collections.emptyList();

                    List<DashboardDto.ActionPointDto> apDtos = actionPoints.stream()
                            .filter(ap -> ap != null && ap.getUser() != null && ap.getUser().getId() != null)
                            .filter(ap -> ap.getUser().getId().equals(userId))
                            .map(ap -> new DashboardDto.ActionPointDto(
                                    ap.getId(),
                                    ap.getActionContent(),
                                    ap.getIsFinished()
                            ))
                            .toList();

                    projectInfo.setMyActionPoints(apDtos);
                    projectInfo.setMyActionPointsCount(apDtos.size());
                } else {
                    // 회의 자체가 null인 경우
                    projectInfo.setLatestMeetingId(null);
                    projectInfo.setMyActionPoints(Collections.emptyList());
                    projectInfo.setMyActionPointsCount(0);
                }
            } else {
                // 최근 회의가 없을 경우
                projectInfo.setLatestMeetingId(null);
                projectInfo.setMyActionPoints(Collections.emptyList());
                projectInfo.setMyActionPointsCount(0);
            }

            projectInfoDtos.add(projectInfo);
        }

        DashboardDto dashboardDto = new DashboardDto();
        dashboardDto.setProjects(projectInfoDtos);
        return dashboardDto;
    }
}
