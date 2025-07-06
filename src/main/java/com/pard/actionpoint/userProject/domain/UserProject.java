package com.pard.actionpoint.userProject.domain;

import com.pard.actionpoint.project.domain.Project;
import com.pard.actionpoint.user.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity @Builder @Getter @AllArgsConstructor @NoArgsConstructor
@Setter // 관계 저장을 위함
public class UserProject {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}
