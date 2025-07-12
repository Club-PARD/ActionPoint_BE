package com.pard.actionpoint.project.domain;

import com.pard.actionpoint.meeting.domain.Meeting;
import com.pard.actionpoint.project.repo.ProjectRepo;
import com.pard.actionpoint.user.domain.User;
import com.pard.actionpoint.userProject.domain.UserProject;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserProject> userProjects = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Meeting> meetings = new ArrayList<>();

    public Project(String projectName, String projectCode, Long ownerId, Integer projectUserCnt, int projectStatus) {
        this.projectName = projectName;
        this.projectCode = projectCode;
        this.ownerId = ownerId;
        this.projectUserCnt = projectUserCnt;
        this.projectStatus = projectStatus;
        this.createdAt = LocalDateTime.now();
    }
}
