package com.pard.actionpoint.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

// 페이지 전체 넘겨줄 데이터
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ProjectDetailDto {
    private String projectName;
    private String projectCode;
    private List<MeetingListDto> meetings;

    // 회의에 대한 내용
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class MeetingListDto {
        private Long meetingId;
        private String meetingTitle;
        private Date meetingDate;
        private int totalActionPoints;
        private int finishedActionPoints;
        private List<ActionPointDto> actionPoints;
    }

    // 액션포인트에 대한 내용
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class ActionPointDto {
        private Long actionPointId;
        private String actionCentent;
        private boolean isFinished;
    }
}
