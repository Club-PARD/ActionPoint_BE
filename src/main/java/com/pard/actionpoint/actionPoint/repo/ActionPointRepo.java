package com.pard.actionpoint.actionPoint.repo;

import com.pard.actionpoint.actionPoint.domain.ActionPoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActionPointRepo extends JpaRepository<ActionPoint, Long> {
    List<ActionPoint> findByMeetingIdAndUserId(Long meetingId, Long userId);
    int countByMeetingId(Long meetingId);
    int countByMeetingIdAndIsFinishedTrue(Long meetingId);
}
