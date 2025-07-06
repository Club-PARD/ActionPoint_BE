package com.pard.actionpoint.agenda.repo;

import com.pard.actionpoint.agenda.domain.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendaRepo extends JpaRepository<Agenda, Long> {

}
