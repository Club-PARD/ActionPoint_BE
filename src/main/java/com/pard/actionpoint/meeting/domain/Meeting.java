package com.pard.actionpoint.meeting.domain;

import com.pard.actionpoint.DTO.MeetingDto;
import com.pard.actionpoint.actionPoint.domain.ActionPoint;
import com.pard.actionpoint.agenda.domain.Agenda;
import com.pard.actionpoint.meetingReference.domain.MeetingReference;
import com.pard.actionpoint.project.domain.Project;
import com.pard.actionpoint.user.domain.User;
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
    private String meetingParticipants;
    private String meetingLastSummary;

    @OneToOne
    @JoinColumn(name = "meeting_writer_id")
    private User writer;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToMany(mappedBy = "meeting", cascade = CascadeType.REMOVE)
    private List<Agenda> agendas = new ArrayList<>();

    @OneToMany(mappedBy = "meeting", cascade = CascadeType.REMOVE)
    private List<ActionPoint> actionPoints = new ArrayList<>();

    @OneToMany(mappedBy = "meeting", cascade = CascadeType.REMOVE)
    private List<MeetingReference> meetingReferences = new ArrayList<>();

    public Meeting(Project project, String meetingTitle, Date meetingDate, String meetingTime, String meetingParticipants, User writer) {
        this.project = project;
        this.meetingTitle = meetingTitle;
        this.meetingDate = meetingDate;
        this.meetingTime = meetingTime;
        this.meetingParticipants = meetingParticipants;
        this.writer = writer;
    }
}
