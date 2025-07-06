package com.pard.actionpoint.userProject.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter @NoArgsConstructor @EqualsAndHashCode
public class UserProjectId implements Serializable {
    private Long user;
    private Long project;
}