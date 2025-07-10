package com.pard.actionpoint.DTO;

import lombok.*;

import java.util.List;

@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class DashboardDto {
    private List<ProjectInfoDto> projects;

    @Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
    public static class ProjectInfoDto {
        private Long projectId;
        private String projectName;
        private Long latestMeetingId;
        private String latestMeetingTitle;
        private String meetingLastSummary;
        private List<ActionPointDto> myActionPoints;
        private int myActionPointsCount;
    }

    @Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
    public static class ActionPointDto {
        private Long actionPointId;
        private String actionContent;
        private boolean isFinished;
    }
}
