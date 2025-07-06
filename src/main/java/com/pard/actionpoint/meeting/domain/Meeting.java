package com.pard.actionpoint.meeting.domain;

import com.pard.actionpoint.DTO.MeetingDto;
import com.pard.actionpoint.actionPoint.domain.ActionPoint;
import com.pard.actionpoint.agenda.domain.Agenda;
import com.pard.actionpoint.meetingParticipant.domain.MeetingParticipant;
import com.pard.actionpoint.meetingReference.domain.MeetingReference;
import com.pard.actionpoint.project.domain.Project;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity @Builder @Getter @AllArgsConstructor @NoArgsConstructor
@Setter // 요약본 저장
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String meetingTitle;
    private Date meetingDate;
    private String meetingTime;
    private String meetingLastSummary;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToMany(mappedBy = "meeting")
    private List<MeetingParticipant> participants = new ArrayList<>();

    @OneToMany(mappedBy = "meeting")
    private List<Agenda> agendas = new ArrayList<>();

    @OneToMany(mappedBy = "meeting")
    private List<ActionPoint> actionPoints = new ArrayList<>();

    @OneToMany(mappedBy = "meeting")
    private List<MeetingReference> meetingReferences = new ArrayList<>();

    public Meeting(String meetingTitle, Date meetingDate, String meetingTime) {
        this.meetingTitle = meetingTitle;
        this.meetingDate = meetingDate;
        this.meetingTime = meetingTime;
    }
}
