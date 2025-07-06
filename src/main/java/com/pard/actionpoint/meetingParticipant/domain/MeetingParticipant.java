package com.pard.actionpoint.meetingParticipant.domain;

import com.pard.actionpoint.meeting.domain.Meeting;
import com.pard.actionpoint.user.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @Builder @Getter @AllArgsConstructor @NoArgsConstructor
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

    public MeetingParticipant(User user, Meeting meeting, Boolean isWriter) {
        this.user = user;
        this.meeting = meeting;
        this.isWriter = false;
    }
}
