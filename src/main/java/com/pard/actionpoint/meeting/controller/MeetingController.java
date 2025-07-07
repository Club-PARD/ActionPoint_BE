package com.pard.actionpoint.meeting.controller;

import com.pard.actionpoint.DTO.MeetingDto;
import com.pard.actionpoint.meeting.service.MeetingService;
import io.swagger.v3.oas.annotations.Operation;
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
    @PostMapping("/title")
    @Operation(summary = "제목, 날짜, 시간, 참여자, 서기, 안건 저장")
    public ResponseEntity<Long> createMeeting(@RequestBody MeetingDto.MeetingCreateDto dto) {
        Long meetingId = meetingService.createMeeting(dto);
        return ResponseEntity.ok(meetingId);
    }

    // 2단계: 안건 상세 내용 저장
    @PatchMapping("/agendas")
    @Operation(summary = "회의록 저장")
    public ResponseEntity<?> updateAgendas(
            @RequestBody List<MeetingDto.AgendaDetailUpdateDto> agendas
    ) {
        meetingService.updateAgendaDetails(agendas);
        return ResponseEntity.ok().build();
    }

    // 3단계: 액션포인트 및 최종 요약 저장
    @PatchMapping("/{meetingId}/summary")
    @Operation(summary = "최종 요약 저장")
    public ResponseEntity<?> actionPoints(
            @PathVariable Long meetingId,
            @RequestBody MeetingDto.MeetingSummaryDto dto
    ) {
        meetingService.updateLastSummary(meetingId, dto);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/{meetingId}/actionpoints")
    @Operation(summary = "액션 포인트 저장")
    public ResponseEntity<?> saveActionPoints(
            @PathVariable Long meetingId,
            @RequestBody List<MeetingDto.ActionPointDto> dto
    ) {
        meetingService.saveActionPoints(meetingId, dto);
        return ResponseEntity.ok().build();
    }
}
