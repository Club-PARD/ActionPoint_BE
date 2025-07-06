package com.pard.actionpoint.actionPoint.repo;

import com.pard.actionpoint.actionPoint.domain.ActionPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionPointRepo extends JpaRepository<ActionPoint, Long> {
}
