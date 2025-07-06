package com.pard.actionpoint.meeting.repo;

import com.pard.actionpoint.meeting.domain.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRepo extends JpaRepository<Meeting, Long> {
}
