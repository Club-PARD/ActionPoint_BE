package com.pard.actionpoint.meeting.controller;

import com.pard.actionpoint.DTO.MeetingDto;
import com.pard.actionpoint.DTO.ProjectDto;
import com.pard.actionpoint.meeting.service.MeetingService;
import com.pard.actionpoint.userProject.service.UserProjectService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meetings")

public class MeetingController {
    private final MeetingService meetingService;
    private final UserProjectService userProjectService;

    // 1단계: 회의 생성
    @PostMapping("/create/title")
    @Operation(summary = "[회의록 작성 1단계] 제목, 날짜, 시간, 참여자, 서기, 파일, 회의 안건 저장",
            description =
                    "회의록 작성 첫 페이지에 대해서 적은 값들을 저장합니다.<br>"
                    + "데이터와 파일을 같이 받아와야 해서 RequestPart를 사용했습니다.<br>"
                    + "파일은 가공 없이 파일 그대로 보내주시면 백엔드 측에서 URL로 전환하여 후에 URL로 반환합니다.<br>"
                    + "EX) https://actionpoint-bucket.s3.ap-northeast-2.amazonaws.com/meeting/reference/{UUID}-{OriginalFileName}.pdf<br>"
                    + "Req : {프로젝트 ID, 회의 제목, 회의 날짜, 회의 시간, 참석자, 작성자 유저 ID, <List> 회의안건 제목}, {<List> 파일}<br>"
                    + "Res : <List> 회의안건 ID"
    )
    public ResponseEntity<List<Long>> createMeeting(
            @RequestPart("data") MeetingDto.MeetingCreateDto dto,
            @RequestPart("files") List<MultipartFile> files
    ) {
        List<Long> agendaIds = meetingService.createMeeting(dto, files != null ? files : new ArrayList<>());
        return ResponseEntity.ok(agendaIds);
    }

    // 2단계: 안건 상세 내용 저장
    @PatchMapping("/create/agendas")
    @Operation(summary = "[회의록 작성 2단계] 회의 안건에 따른 회의록 저장",
            description = "아젠다 ID 마다 TEXT 내용을 연결시킵니다.<br>" +
                    "Req : <List> {회의안건 ID, 회의안건 내용}<br>" +
                    "Res : 회의록 ID"
    )
    public ResponseEntity<Long> updateAgendas(
            @RequestBody List<MeetingDto.AgendaDetailUpdateDto> agendas
    ) {
        Long meetingId = meetingService.updateAgendaDetails(agendas);
        return ResponseEntity.ok(meetingId);
    }

    // 3단계: 액션포인트 및 최종 요약 저장
    @PatchMapping("/create/{meetingId}/summary")
    @Operation(summary = "[회의록 작성 3-1단계] 추가 논의 저장",
            description = "이 단계에서는 추가 논의와 액션포인트를 저장합니다.<br>" +
                    "하지만 두 요청의 방식이 다르기 때문에 분리하도록 합니다. 한번에 두 방식의 요청을 보내주시기 바랍니다.<br>" +
                    "Req : 회의 ID(URL Path), 추가 논의<br>" +
                    "Res : ")
    public ResponseEntity<?> actionPoints(
            @PathVariable Long meetingId,
            @RequestBody MeetingDto.MeetingSummaryDto dto
    ) {
        meetingService.updateLastSummary(meetingId, dto);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/create/{meetingId}/actionpoints")
    @Operation(summary = "[회의록 작성 3-2단계] 액션 포인트 저장",
            description = "이 단계에서는 추가 논의와 액션포인트를 저장합니다.<br>" +
                    "하지만 두 요청의 방식이 다르기 대문에 분리하도록 합니다. 한번에 두 방식의 요청을 보내주시기 바랍니다.<br>" +
                    "Req : 회의 ID(URL Path), <List>{액션포인트 내용, 사용자 ID} -> 완료 상태는 요청 필요 X<br>" +
                    "Res : ")
    public ResponseEntity<?> saveActionPoints(
            @PathVariable Long meetingId,
            @RequestBody List<MeetingDto.ActionPointDto> dto
    ) {
        meetingService.saveActionPoints(meetingId, dto);
        return ResponseEntity.ok().build();
    }

    // 회의록 삭제
    @DeleteMapping("/{meetingId}")
    @Operation(summary = "[회의록 삭제]",
            description = "회의록을 삭제합니다.<br>" +
                    "Req : 회의 ID (URL Path)<br>" +
                    "Res : ")
    public ResponseEntity<?> deleteMeeting(@PathVariable Long meetingId) {
        meetingService.deleteMeeting(meetingId);
        return ResponseEntity.noContent().build();
    }

    // 회의 상세 조회
    @GetMapping("/{meetingId}")
    @Operation(summary = "[회의록 조회]",
            description = "저장된 회의록에 대한 모든 정보를 넘깁니다. 수정 부분에서 사용됩니다.<br>" +
                    "Req : 회의 ID (URL Path)<br>" +
                    "Res : 회의 ID, 회의 제목, 회의 날짜, 회의 시간, 작성자 ID, 작성자 이름, 참석자, <List> {참고자료 URL},\n" +
                    "<List> {회의안건 ID, 회의 안건 제목, 회의 안건 내용}, <List> {액션포인트 ID, 액션포인트 내용, 유저 ID, 유저 이름, 완료 여부}, 추가 안건")
    public ResponseEntity<MeetingDto.MeetingDetailDto> getMeetingDetail(@PathVariable Long meetingId) {
        MeetingDto.MeetingDetailDto dto = meetingService.getMeetingDetail(meetingId);
        return ResponseEntity.ok().body(dto);
    }

    // 회의록 수정
    @PatchMapping("/edit")
    @Operation(summary = "[회의록 수정]",
            description = "회의록은 추가 논의, 아젠다 내용, 액션포인트 내용만 수정 가능하며 액션포인트는 추가도 가능합니다.<br>" +
                    "회의 ID 잊지 않고 넘겨주세요.<br>" +
                    "PATCH로 액션포인트 수정과 생성을 한번에 처리할거라 두 액션포인트 부분을 따로 리스트화해서 보내주셔야 합니다.<br>" +
                    "Req : 회의 ID, 추가 논의, <List> {회의안건 ID, 회의안건 내용}, <List> {액션포인트 ID, 액션포인트 내용, 완료상태}, <List> {액션포인트 내용, 유저 ID, 완료상태(false)}\n" +
                    "Res : ")
    public ResponseEntity<?> updateMeeting(
            @RequestBody MeetingDto.MeetingUpdateRequestDto dto
    ) {
        meetingService.updateMeeting(dto);
        return ResponseEntity.ok().build();
    }

    // 프로젝트 참석자 리스트
    @GetMapping("/{projectId}/users")
    @Operation(summary = "[유저 리스트 조회]",
            description = "프로젝트에 해당하는 유저 리스트를 조회합니다. 언급을 통해 회의 참석자 및 작성자를 지정할 때 사용합니다.<br>" +
                    "Req : 프로젝트 ID (URL Path)<br>" +
                    "Res : 프로젝트 ID, <List> {유저 ID, 유저 이름, 유저 이메일}")
    public ResponseEntity<ProjectDto.ProjectUserListDto> getProjectUsers(
            @PathVariable Long projectId
    ) {
        ProjectDto.ProjectUserListDto response = userProjectService.getUserProjectList(projectId);
        return ResponseEntity.ok().body(response);
    }
}
