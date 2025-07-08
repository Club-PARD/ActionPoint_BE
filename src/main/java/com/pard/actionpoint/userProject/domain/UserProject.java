package com.pard.actionpoint.userProject.domain;

import com.pard.actionpoint.project.domain.Project;
import com.pard.actionpoint.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity @Builder @Getter @AllArgsConstructor @NoArgsConstructor
@Setter // 관계 저장을 위함
@IdClass(UserProjectId.class)
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
