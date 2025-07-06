package com.pard.actionpoint.meetingReference.repo;

import com.pard.actionpoint.meetingReference.domain.MeetingReference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingReferenceRepo extends JpaRepository<MeetingReference, Long> {
}
