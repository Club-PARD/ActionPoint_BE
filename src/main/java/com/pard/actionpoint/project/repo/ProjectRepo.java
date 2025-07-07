package com.pard.actionpoint.project.repo;

import com.pard.actionpoint.project.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepo extends JpaRepository<Project, Long> {
    // 프로젝트 생성 단계
    boolean existsByProjectCode(String projectCode);

    // 프로젝트 참여 단계
    Optional<Project> findByProjectCode(String projectCode);

    // 프로젝트 내부 페이지 단계
    Optional<Project> findById(Long id);
}
