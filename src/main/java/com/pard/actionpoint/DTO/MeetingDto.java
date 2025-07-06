package com.pard.actionpoint.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MeetingDto {
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public class MeetingCreateDto {
        private String meetingTitle;
        private Date meetingDate;
        private String meetingTime;

        private List<Long> participantIds;
        private Long writerId;

        private List<String> agendaTitles;
        private List<String> referenceUrls;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public class AgendaDetailUpdateDto{
        private Long agendaId;
        private String agendaContent;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public class ActionPointUpdateDto{
        private Long meetingId;
        private String meetingLastSummary;

        private List<ActionPointDto> actionPoints;


    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class ActionPointDto{
        private String actionContent;
        private Long userId;
        private boolean isFinished;
    }
}
