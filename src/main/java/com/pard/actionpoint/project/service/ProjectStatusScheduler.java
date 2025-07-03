package com.pard.actionpoint.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectStatusScheduler {

    private final ProjectService projectService;

    // 매일 자정 (00:00)에 실행
    @Scheduled(cron = "0 0 0 * * *")
    public void updateProjectStatusesDaily() {
//        projectService.updateAllProjectStatuses();
    }
}
