package com.pard.actionpoint.actionPoint.domain;

import com.pard.actionpoint.meeting.domain.Meeting;
import com.pard.actionpoint.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity @Builder @Getter @AllArgsConstructor @NoArgsConstructor
@Setter // 토글을 위함
public class ActionPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String actionContent;
    private Boolean isFinished;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    public ActionPoint(String actioncontent, User user, boolean isFinished, Meeting meeting) {
        this.actionContent = actioncontent;
        this.user = user;
        this.isFinished = false;
        this.meeting = meeting;
    }
}
