package com.pard.actionpoint.actionPoint.service;

import com.pard.actionpoint.actionPoint.domain.ActionPoint;
import com.pard.actionpoint.actionPoint.repo.ActionPointRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor

public class ActionPointService {
    private final ActionPointRepo actionPointRepo;

    // 액션포인트 토글
    @Transactional
    public void toggleActionPoint(Long id) {
        ActionPoint ap = actionPointRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("ActionPoint not found"));

        ap.setIsFinished(!ap.getIsFinished());
    }
}
