package com.pard.actionpoint.project.domain;

import com.pard.actionpoint.project.repo.ProjectRepo;
import com.pard.actionpoint.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity @Getter @NoArgsConstructor @AllArgsConstructor @Builder
@Setter // 유저가 0명이면 프로젝트를 삭제하기 위해 사용
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String projectName;

    @Column(length = 6, unique = true)
    private String projectCode;
    private Long ownerId;
    private Integer projectUserCnt;
    private int projectStatus; // 0 : 활성화, 1 : 새로만든 프로젝트 (회의 없음), 2 : 회의가 오래된 프로젝트

    @ManyToMany(mappedBy = "projects")
    private List<User> users;

    public Project(String projectName, String projectCode, Long ownerId, Integer projectUserCnt, int projectStatus) {
        this.projectName = projectName;
        this.projectCode = projectCode;
        this.ownerId = ownerId;
        this.projectUserCnt = projectUserCnt;
        this.projectStatus = projectStatus;
    }
}
