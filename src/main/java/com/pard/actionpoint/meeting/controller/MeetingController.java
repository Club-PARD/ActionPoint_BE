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

    // 회의록 삭제
    @DeleteMapping("/{meetingId}")
    @Operation(summary = "회의록 삭제")
    public ResponseEntity<?> deleteMeeting(@PathVariable Long meetingId) {
        meetingService.deleteMeeting(meetingId);
        return ResponseEntity.noContent().build();
    }

    // 회의 상세 조회
    @GetMapping("/{meetingId}")
    @Operation(summary = "회의 상세 보기 (조회 or 수정)")
    public ResponseEntity<MeetingDto.MeetingDetailDto> getMeetingDetail(@PathVariable Long meetingId) {
        MeetingDto.MeetingDetailDto dto = meetingService.getMeetingDetail(meetingId);
        return ResponseEntity.ok().body(dto);
    }

    // 회의록 수정
    @PatchMapping("/edit")
    @Operation(summary = "회의록 수정", description = "추가 논의, 아젠다 내용, 액션포인트 수정 및 추가 / JSON에 meetingId 명시 필요합니다 + PATCH로 액션포인트 수정과 생성을 한번에 처리할거라 두 액션포인트 부분을 따로 리스트화해서 보내주셔야 합니다.")
    public ResponseEntity<?> updateMeeting(
            @RequestBody MeetingDto.MeetingUpdateRequestDto dto
    ) {
        meetingService.updateMeeting(dto);
        return ResponseEntity.ok().build();
    }
}
