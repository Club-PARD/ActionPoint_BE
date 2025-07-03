package com.pard.actionpoint.project.domain;

import com.pard.actionpoint.project.repo.ProjectRepo;
import com.pard.actionpoint.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity @Getter @NoArgsConstructor @AllArgsConstructor @Builder
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String projectName;
    private String projectCode;
    private Long ownerId;
    private Integer projectUserCnt;
    private int status;

    @ManyToMany(mappedBy = "projects")
    private List<User> users;
}
