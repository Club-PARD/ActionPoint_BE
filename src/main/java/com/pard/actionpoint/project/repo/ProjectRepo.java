package com.pard.actionpoint.project.repo;

import com.pard.actionpoint.project.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectRepo extends JpaRepository<Project, Long> {
    // 프로젝트 생성 단계
    boolean existsByProjectCode(String projectCode);

    // 프로젝트 참여 단계
    Optional<Project> findByProjectCode(String projectCode);

    // 프로젝트 내부 페이지 단계
    Optional<Project> findById(Long id);

    // 유저가 속한 프로젝트 리스트 조회
    @Query("SELECT p FROM Project p JOIN UserProject pp ON p.id = pp.project.id WHERE pp.user.id = :userId")
    List<Project> findProjectsByUserId(@Param("userId") Long userId);

    // 프로젝트 리스트 조회
    @Query("SELECT p FROM Project p JOIN UserProject pp ON p.id = pp.project.id WHERE pp.user.id = :userId ORDER BY p.createdAt DESC")
    List<Project> findProjectsByUserIdOrderByCreatedAtDesc(@Param("userId") Long userId);
}
