package com.pard.actionpoint.user.domain;

import com.pard.actionpoint.project.domain.Project;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @ManyToMany
    @JoinTable(
            name = "user_project",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private List<Project> projects;
}
