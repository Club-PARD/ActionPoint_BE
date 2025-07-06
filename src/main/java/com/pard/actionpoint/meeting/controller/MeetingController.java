package com.pard.actionpoint.meeting.controller;

import com.pard.actionpoint.DTO.MeetingDto;
import com.pard.actionpoint.meeting.service.MeetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meetings")

public class MeetingController {
    private final MeetingService meetingService;

    // 1단계: 회의 생성
    @PostMapping("/start")
    public ResponseEntity<Long> createMeeting(@RequestBody MeetingDto.MeetingCreateDto dto) {
        Long meetingId = meetingService.createMeeting(dto);
        return ResponseEntity.ok(meetingId);
    }

    // 2단계: 안건 상세 내용 저장
    @PatchMapping("/agendas")
    public ResponseEntity<?> updateAgendas(
            @RequestBody List<MeetingDto.AgendaDetailUpdateDto> agendas
    ) {
        meetingService.updateAgendaDetails(agendas);
        return ResponseEntity.ok().build();
    }

    // 3단계: 액션포인트 및 최종 요약 저장
    @PatchMapping("/actionpoints")
    public ResponseEntity<?> actionPoints(
            @RequestBody MeetingDto.ActionPointUpdateDto dto
    ) {
        meetingService.updateActionPoint(dto);
        return ResponseEntity.ok().build();
    }
}
