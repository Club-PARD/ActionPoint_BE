package com.pard.actionpoint.userProject.service;

import com.pard.actionpoint.DTO.ProjectDto;
import com.pard.actionpoint.user.domain.User;
import com.pard.actionpoint.userProject.domain.UserProject;
import com.pard.actionpoint.userProject.repo.UserProjectRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class UserProjectService {
    private final UserProjectRepo userProjectRepo;

    public ProjectDto.ProjectUserListDto getUserProjectList(Long projectId) {
        List<UserProject> participants = userProjectRepo.findByProjectId(projectId);

        List<ProjectDto.ProjectUserDto> userDto = participants.stream()
                .map(participant -> {
                    User user = participant.getUser();
                    return new ProjectDto.ProjectUserDto(
                            user.getId(),
                            user.getUserName(),
                            user.getUserEmail()
                    );
                }).collect(Collectors.toList());

        return new ProjectDto.ProjectUserListDto(projectId, userDto);
    }
}
