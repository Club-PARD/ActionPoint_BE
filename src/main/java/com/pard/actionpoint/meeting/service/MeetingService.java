package com.pard.actionpoint.meeting.service;

import com.pard.actionpoint.DTO.MeetingDto;
import com.pard.actionpoint.actionPoint.domain.ActionPoint;
import com.pard.actionpoint.actionPoint.repo.ActionPointRepo;
import com.pard.actionpoint.agenda.domain.Agenda;
import com.pard.actionpoint.agenda.repo.AgendaRepo;
import com.pard.actionpoint.common.s3.S3Uploader;
import com.pard.actionpoint.global.exception.BadRequestException;
import com.pard.actionpoint.meeting.domain.Meeting;
import com.pard.actionpoint.meeting.repo.MeetingRepo;
import com.pard.actionpoint.meetingReference.domain.MeetingReference;
import com.pard.actionpoint.meetingReference.repo.MeetingReferenceRepo;
import com.pard.actionpoint.project.domain.Project;
import com.pard.actionpoint.project.repo.ProjectRepo;
import com.pard.actionpoint.user.domain.User;
import com.pard.actionpoint.user.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class MeetingService {
    private final UserRepo userRepo;
    private final MeetingRepo meetingRepo;
    private final AgendaRepo agendaRepo;
    private final MeetingReferenceRepo meetingReferenceRepo;
    private final ActionPointRepo actionPointRepo;
    private final ProjectRepo projectRepo;

    private final S3Uploader s3Uploader;

    // (회의록 작성) 첫 페이지
    // 참고자료 S3 처리
    @Transactional
    public List<MeetingDto.AgendaDetailUpdateResDto> createMeeting(MeetingDto.MeetingCreateDto dto, List<MultipartFile> files) {
        List<String> referenceUrls = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                String url = s3Uploader.upload(file, "meeting/reference");
                referenceUrls.add(url);
            } catch (IOException e) {
                throw new RuntimeException("S3 파일 업로드 실패", e);
            }
        }
        dto.setReferenceUrls(referenceUrls);

        // 회의 저장
        Project project = projectRepo.findById(dto.getProjectId())
                .orElseThrow(() -> new BadRequestException("Project not found"));

        User writer = userRepo.findById(dto.getMeetingWriterId())
                .orElseThrow(() -> new BadRequestException("User not found"));

        Meeting meeting = new Meeting(
                project,
                dto.getMeetingTitle(),
                dto.getMeetingDate(),
                dto.getMeetingTime(),
                dto.getMeetingParticipants(),
                writer
        );
        meetingRepo.save(meeting);

        // 안건 저장 및 ID 리스트 수집
        List<MeetingDto.AgendaDetailUpdateResDto> agendaDtos = new ArrayList<>();
        for (String title : dto.getAgendaTitles()) {
            Agenda agenda = agendaRepo.save(new Agenda(title, meeting));
            agendaDtos.add(new MeetingDto.AgendaDetailUpdateResDto(agenda.getId(), agenda.getAgendaTitle()));
        }

        // 참고자료 저장
        for (String url : dto.getReferenceUrls()) {
            meetingReferenceRepo.save(new MeetingReference(meeting, url));
        }

        return agendaDtos; // 👉 프론트에 이 리스트만 반환
    }


    // (회의록 작성) 두번째 페이지
    // 안건에 대한 회의록 업데이트
    @Transactional
    public Long updateAgendaDetails(List<MeetingDto.AgendaDetailUpdateDto> agendaList){
        Long meetingId = null;

        for(MeetingDto.AgendaDetailUpdateDto agendaDto : agendaList){
            Agenda agenda = agendaRepo.findById(agendaDto.getAgendaId())
                    .orElseThrow(() -> new RuntimeException("Agenda not found"));
            agenda.setAgendaContent(agendaDto.getAgendaContent());

            if(meetingId == null){
                meetingId = agenda.getMeeting().getId(); // 첫번째 아젠다를 처리할 때 meetingId 한번만 가져옴
            }
        }

        return meetingId;
    }

    // (회의록 작성) 끝 페이지
    // 최종 회의 요약 업데이트
    @Transactional
    public void updateLastSummary(Long meetingId, MeetingDto.MeetingSummaryDto meetingSummaryDto){
        Meeting meeting = meetingRepo.findById(meetingId)
                .orElseThrow(() -> new RuntimeException("Meeting not found"));
        meeting.setMeetingLastSummary(meetingSummaryDto.getMeetingLastSummary());
    }
    // 액션 포인트 저장
    @Transactional
    public void saveActionPoints(Long meetingId, List<MeetingDto.ActionPointDto> actionPoints) {
        Meeting meeting = meetingRepo.findById(meetingId)
                .orElseThrow(() -> new RuntimeException("Meeting not found"));

        for (MeetingDto.ActionPointDto ap : actionPoints) {
            User user = userRepo.findById(ap.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            ActionPoint actionPoint = new ActionPoint(
                    ap.getActionContent(),
                    user,
                    false, // 초기 상태는 무조건 false
                    meeting
            );

            actionPointRepo.save(actionPoint);
        }
    }

    // 회의록 삭제
    @Transactional
    public void deleteMeeting(Long meetingId) {
        Meeting meeting = meetingRepo.findById(meetingId)
                .orElseThrow(() -> new RuntimeException("Meeting not found"));

        meetingRepo.delete(meeting);
    }

    // 회의 Detail 페이지
    @Transactional
    public MeetingDto.MeetingDetailDto getMeetingDetail(Long meetingId) {
        Meeting meeting =meetingRepo.findById(meetingId)
                .orElseThrow(() -> new RuntimeException("Meeting not found"));

        // 기본 회의 정보
        MeetingDto.MeetingDetailDto dto = new MeetingDto.MeetingDetailDto();
        dto.setMeetingId(meeting.getId());
        dto.setMeetingTitle(meeting.getMeetingTitle());
        dto.setMeetingDate(meeting.getMeetingDate());
        dto.setMeetingTime(meeting.getMeetingTime());
        dto.setMeetingParticipants(meeting.getMeetingParticipants());
        dto.setMeetingLastSummary(meeting.getMeetingLastSummary());

        // 서기
        User writer = meeting.getWriter();
        if(writer != null){
            MeetingDto.MeetingDetailDto.UserDto writerDto = new MeetingDto.MeetingDetailDto.UserDto();
            writerDto.setUserId(writer.getId());
            writerDto.setUserName(writer.getUserName());
            dto.setMeetingWriter(writerDto);
        } else {
            dto.setMeetingWriter(null);
        }

        // 참고자료
        List<String> urls = meetingReferenceRepo.findByMeeting(meeting).stream()
                .map(MeetingReference::getReferenceUrl)
                .collect(Collectors.toList());
        dto.setReferenceUrls(urls);

        // 회의 안건
        List<MeetingDto.MeetingDetailDto.AgendaDto> agendas = agendaRepo.findByMeeting(meeting).stream()
                .map(a -> {
                    MeetingDto.MeetingDetailDto.AgendaDto ag = new MeetingDto.MeetingDetailDto.AgendaDto();
                    ag.setAgendaId(a.getId());
                    ag.setAgendaTitle(a.getAgendaTitle());
                    ag.setAgendaContent(a.getAgendaContent());
                    return ag;
                }).collect(Collectors.toList());
        dto.setAgendas(agendas);

        // 액션 포인트
        List<MeetingDto.MeetingDetailDto.ActionPointDto> aps = actionPointRepo.findByMeeting(meeting).stream()
                .map(ap -> {
                    MeetingDto.MeetingDetailDto.ActionPointDto apDto = new MeetingDto.MeetingDetailDto.ActionPointDto();
                    apDto.setActionPointId(ap.getId());
                    apDto.setUserId(ap.getUser().getId());
                    apDto.setUserName(ap.getUser().getUserName());
                    apDto.setActionContent(ap.getActionContent());
                    return apDto;
                }).collect(Collectors.toList());
        dto.setActionPoints(aps);

        return dto;
    }

    // 회의록 수정
    @Transactional
    public void updateMeeting(MeetingDto.MeetingUpdateRequestDto dto){
        Meeting meeting = meetingRepo.findById(dto.getMeetingId())
                .orElseThrow(() -> new RuntimeException("Meeting not found"));

        // 회의 요약 수정
        meeting.setMeetingLastSummary(dto.getMeetingLastSummary());

        // 회의 안건 수정
        for (MeetingDto.MeetingUpdateRequestDto.AgendaUpdateDto agendaDto : dto.getAgendas()) {
            Agenda agenda = agendaRepo.findById(agendaDto.getAgendaId())
                    .orElseThrow(() -> new RuntimeException("Agenda not found"));
            agenda.setAgendaContent(agendaDto.getAgendaContent());
        }

        // 기존 액션포인트 수정
        for (MeetingDto.MeetingUpdateRequestDto.ActionPointUpdateDto apDto : dto.getActionPoints()){
            ActionPoint ap = actionPointRepo.findById(apDto.getActionPointId())
                    .orElseThrow(() -> new RuntimeException("ActionPoint not found"));
            ap.setActionContent(apDto.getActionContent());
            ap.setIsFinished(false);
        }

        // 새 액션포인트 추가
        for (MeetingDto.MeetingUpdateRequestDto.ActionPointCreateDto newAp : dto.getNewActionPoints()){
            User user = userRepo.findById(newAp.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            ActionPoint ap = new ActionPoint(newAp.getActionContent(), user, false, meeting);
            actionPointRepo.save(ap);
        }
    }
}
