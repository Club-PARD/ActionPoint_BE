package com.pard.actionpoint.agenda.domain;

import com.pard.actionpoint.meeting.domain.Meeting;
import jakarta.persistence.*;
import lombok.*;

@Entity @Builder @Getter @AllArgsConstructor @NoArgsConstructor
@Setter // 단계별 회의록 정리에 필요함
public class Agenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String agendaTitle;
    private String agendaContent;
    private String agendaSummary;

    @ManyToOne
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    public Agenda(String agendaTitle, Meeting meeting){
        this.agendaTitle = agendaTitle;
        this.meeting = meeting;
    }
}
