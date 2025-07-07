package com.pard.actionpoint.userProject.repo;

import com.pard.actionpoint.project.domain.Project;
import com.pard.actionpoint.user.domain.User;
import com.pard.actionpoint.userProject.domain.UserProject;
import com.pard.actionpoint.userProject.domain.UserProjectId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProjectRepo extends JpaRepository<UserProject, UserProjectId> {
    boolean existsByUserAndProject(User user, Project project);

    Optional<UserProject> findByUserAndProject(User user, Project project);
}
