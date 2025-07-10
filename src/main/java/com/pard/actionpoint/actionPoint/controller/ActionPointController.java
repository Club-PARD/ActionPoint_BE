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
    @Operation(summary = "[액션 포인트 상태 변환] 액션포인트 체크 표시에 대한 토글<br>",
            description =
                    "액션포인트의 체크 표시를 누를 때마다 상태를 변환합니다.<br>"
                    + "Req : 액션 포인트 ID<br>"
                    + "Res : "
    )
    public ResponseEntity<Void> toggleActionPoint(@PathVariable Long id) {
        actionPointService.toggleActionPoint(id);
        return ResponseEntity.ok().build();
    }
}
