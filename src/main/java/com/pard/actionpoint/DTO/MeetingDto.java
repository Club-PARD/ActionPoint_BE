package com.pard.actionpoint.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MeetingDto {
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class MeetingCreateDto {
        private Long projectId;

        private String meetingTitle;
        private Date meetingDate;
        private String meetingTime;

        private String meetingParticipants;
        private Long meetingWriterId;

        private List<String> agendaTitles;

        @Schema(hidden = true) // 프론트가 보내지 않아도 되는 필드
        private List<String> referenceUrls;
    }

    // 회의록 생성 단계에서 프론트가 받아야 하는 값들
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class AgendaDetailUpdateResDto{
        private Long agendaId;
        private String agendaTitle;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class AgendaDetailUpdateDto{
        private Long agendaId;
        private String agendaContent;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public class MeetingSummaryDto{
        private String meetingLastSummary;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class ActionPointDto{
        private Long actionPointId;
        private String actionContent;
        private Long userId;
        private String userName;
        private boolean isFinished;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class MeetingDetailDto {
        private Long meetingId;
        private String meetingTitle;
        private Date meetingDate;
        private String meetingTime;
        private UserDto meetingWriter;
        private String meetingParticipants;
        private List<String> referenceUrls;
        private List<AgendaDto> agendas;
        private List<ActionPointDto> actionPoints;
        private String meetingLastSummary;

        @Getter @Setter
        public static class UserDto {
            private Long userId;
            private String userName;
        }

        @Getter @Setter
        public static class AgendaDto {
            private Long agendaId;
            private String agendaTitle;
            private String agendaContent;
        }

        @Getter @Setter
        public static class ActionPointDto {
            private Long actionPointId;
            private Long userId;
            private String userName;
            private String actionContent;
        }
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class MeetingUpdateRequestDto {
        private Long meetingId;
        private String meetingLastSummary;

        private List<AgendaUpdateDto> agendas; // 기존 아젠다 수정
        private List<ActionPointUpdateDto> actionPoints; // 기존 액션포인트 수정
        private List<ActionPointCreateDto> newActionPoints; // 새 액션포인트 추가

        @Getter @Setter
        public static class AgendaUpdateDto {
            private Long agendaId;
            private String agendaContent;
        }

        @Getter @Setter
        public static class ActionPointUpdateDto {
            private Long actionPointId;
            private String actionContent;
            private Boolean isFinished;
        }

        @Getter @Setter
        public static class ActionPointCreateDto {
            private String actionContent;
            private Long userId;
            private Boolean isFinished;
        }
    }
}
