package com.pard.actionpoint.meetingParticipant.repo;

import com.pard.actionpoint.meeting.domain.Meeting;
import com.pard.actionpoint.meetingParticipant.domain.MeetingParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingParticipantRepo extends JpaRepository<MeetingParticipant, Long> {
    // 회의 Detail
    List<MeetingParticipant> findByMeeting(Meeting meeting);
}
