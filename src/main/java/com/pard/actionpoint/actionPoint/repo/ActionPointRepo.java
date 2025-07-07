package com.pard.actionpoint.actionPoint.repo;

import com.pard.actionpoint.actionPoint.domain.ActionPoint;
import com.pard.actionpoint.meeting.domain.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActionPointRepo extends JpaRepository<ActionPoint, Long> {
    List<ActionPoint> findByMeetingIdAndUserId(Long meetingId, Long userId);

    // 유저의 특정 회의에 대한 액션포인트 조회
    List<ActionPoint> findByUserIdAndMeetingId(Long userId, Long meetingId);

    // 회의 Detail
    List<ActionPoint> findByMeeting(Meeting meeting);
}
