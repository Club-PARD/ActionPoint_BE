package com.pard.actionpoint.project.repo;

import com.pard.actionpoint.project.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepo extends JpaRepository<Project, Long>, ProjectRepoCustom {

}
