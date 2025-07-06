package com.pard.actionpoint.meetingParticipant.repo;

import com.pard.actionpoint.meetingParticipant.domain.MeetingParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingParticipantRepo extends JpaRepository<MeetingParticipant, Long> {
}
