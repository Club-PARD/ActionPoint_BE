package com.pard.actionpoint.agenda.repo;

import com.pard.actionpoint.agenda.domain.Agenda;
import com.pard.actionpoint.meeting.domain.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgendaRepo extends JpaRepository<Agenda, Long> {
    // 회의 Detail
    List<Agenda> findByMeeting(Meeting meeting);
}
