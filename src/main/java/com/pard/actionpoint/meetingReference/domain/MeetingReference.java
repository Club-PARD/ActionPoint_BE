package com.pard.actionpoint.meetingReference.domain;

import com.pard.actionpoint.meeting.domain.Meeting;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @Builder @Getter @AllArgsConstructor @NoArgsConstructor
public class MeetingReference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String referenceUrl;

    @ManyToOne
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    public MeetingReference(Meeting meeting, String referenceUrl) {
        this.meeting = meeting;
        this.referenceUrl = referenceUrl;
    }
}
