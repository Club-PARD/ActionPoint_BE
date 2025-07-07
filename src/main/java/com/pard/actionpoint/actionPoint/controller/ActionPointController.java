package com.pard.actionpoint.actionPoint.controller;

import com.pard.actionpoint.actionPoint.service.ActionPointService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/acitonpoints")

public class ActionPointController {
    private final ActionPointService actionPointService;

    @PatchMapping("/{id}/toggle")
    @Operation(summary = "액션포인트 토글")
    public ResponseEntity<Void> toggleActionPoint(@PathVariable Long id) {
        actionPointService.toggleActionPoint(id);
        return ResponseEntity.ok().build();
    }
}
