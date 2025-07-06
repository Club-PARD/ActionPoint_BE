package com.pard.actionpoint.meetingParticipant.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter @NoArgsConstructor @EqualsAndHashCode
public class MeetingParticipantId implements Serializable {
    private Long user;
    private Long meeting;
}
