package com.pard.actionpoint.user.domain;

import com.pard.actionpoint.actionPoint.domain.ActionPoint;
import com.pard.actionpoint.project.domain.Project;
import com.pard.actionpoint.userProject.domain.UserProject;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity @Builder @Getter @AllArgsConstructor @NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // AUTO INCREMENT
    private String userName;

    @Column(nullable = false, unique = true)
    private String userEmail;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.USER; // ADMIN, USER

    private String socialId; // 구글에서 부여하는 ID

    @OneToMany(mappedBy = "user")
    private List<UserProject> userProjects = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<ActionPoint> actionPoints = new ArrayList<>();
}
