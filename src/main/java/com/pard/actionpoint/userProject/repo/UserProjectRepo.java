package com.pard.actionpoint.userProject.repo;

import com.pard.actionpoint.project.domain.Project;
import com.pard.actionpoint.user.domain.User;
import com.pard.actionpoint.userProject.domain.UserProject;
import com.pard.actionpoint.userProject.domain.UserProjectId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserProjectRepo extends JpaRepository<UserProject, UserProjectId> {
    boolean existsByUserAndProject(User user, Project project);

    Optional<UserProject> findByUserAndProject(User user, Project project);
    List<UserProject> findByProjectId(Long projectId);

    // 프로젝트에 속한 유저 수 세기 위한 메서드
    int countByProject(Project project);
}
