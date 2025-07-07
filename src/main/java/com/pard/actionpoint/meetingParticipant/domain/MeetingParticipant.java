package com.pard.actionpoint.meetingParticipant.domain;

import com.pard.actionpoint.meeting.domain.Meeting;
import com.pard.actionpoint.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @Builder @Getter @AllArgsConstructor @NoArgsConstructor
@IdClass(MeetingParticipantId.class)
public class MeetingParticipant {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    private Boolean isWriter;
}
