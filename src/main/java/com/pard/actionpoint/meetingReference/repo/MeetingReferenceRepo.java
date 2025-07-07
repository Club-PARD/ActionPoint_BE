package com.pard.actionpoint.meetingReference.repo;

import com.pard.actionpoint.meeting.domain.Meeting;
import com.pard.actionpoint.meetingReference.domain.MeetingReference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingReferenceRepo extends JpaRepository<MeetingReference, Long> {
    // 회의 Detail
    List<MeetingReference> findByMeeting(Meeting meeting);
}
