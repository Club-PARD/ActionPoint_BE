package com.pard.actionpoint.meeting.repo;

import com.pard.actionpoint.meeting.domain.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingRepo extends JpaRepository<Meeting, Long> {
    List<Meeting> findAllByProjectIdOrderByMeetingDateDesc(Long projectId);
}
