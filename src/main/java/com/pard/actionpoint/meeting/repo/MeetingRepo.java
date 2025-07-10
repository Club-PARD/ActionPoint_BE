package com.pard.actionpoint.meeting.repo;

import com.pard.actionpoint.meeting.domain.Meeting;
import com.pard.actionpoint.project.domain.Project;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MeetingRepo extends JpaRepository<Meeting, Long> {
    @Query("SELECT m FROM Meeting m WHERE m.project.id = :projectId ORDER BY m.meetingDate DESC")
    List<Meeting> findRecentMeetingByProjectId(@Param("projectId") Long projectId, Pageable pageable);

    // 회의 Detail
    Optional<Meeting> findById(Long id);

    List<Meeting> findByProject(Project project);

    List<Meeting> findByProjectId(Long projectId);
}
