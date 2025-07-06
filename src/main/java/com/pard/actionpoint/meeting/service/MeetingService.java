package com.pard.actionpoint.meeting.service;

import com.pard.actionpoint.DTO.MeetingDto;
import com.pard.actionpoint.actionPoint.domain.ActionPoint;
import com.pard.actionpoint.actionPoint.repo.ActionPointRepo;
import com.pard.actionpoint.agenda.domain.Agenda;
import com.pard.actionpoint.agenda.repo.AgendaRepo;
import com.pard.actionpoint.global.exception.BadRequestException;
import com.pard.actionpoint.meeting.domain.Meeting;
import com.pard.actionpoint.meeting.repo.MeetingRepo;
import com.pard.actionpoint.meetingParticipant.domain.MeetingParticipant;
import com.pard.actionpoint.meetingParticipant.domain.MeetingParticipantId;
import com.pard.actionpoint.meetingParticipant.repo.MeetingParticipantRepo;
import com.pard.actionpoint.meetingReference.domain.MeetingReference;
import com.pard.actionpoint.meetingReference.repo.MeetingReferenceRepo;
import com.pard.actionpoint.user.domain.User;
import com.pard.actionpoint.user.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;
import java.util.List;

@Service
@RequiredArgsConstructor

public class MeetingService {
    private final UserRepo userRepo;
    private final MeetingRepo meetingRepo;
    private final MeetingParticipantRepo meetingParticipantRepo;
    private final AgendaRepo agendaRepo;
    private final MeetingReferenceRepo meetingReferenceRepo;
    private final ActionPointRepo actionPointRepo;

    @Transactional
    public Long createMeeting(MeetingDto.MeetingCreateDto meetingCreateDto) {
        // 회의 저장
        Meeting meeting = new Meeting(meetingCreateDto.getMeetingTitle(), meetingCreateDto.getMeetingDate(), meetingCreateDto.getMeetingTime());
        meetingRepo.save(meeting);

        for (Long userId : meetingCreateDto.getParticipantIds()){
            boolean isWriter = userId.equals(meetingCreateDto.getWriterId());
            User user = userRepo.findById(userId)
                            .orElseThrow(() -> new BadRequestException("User not found"));

            meetingParticipantRepo.save(new MeetingParticipant(user, meeting, isWriter));
        }

        for (String title : meetingCreateDto.getAgendaTitles()){
            agendaRepo.save(new Agenda(title, meeting));
        }

        for (String url : meetingCreateDto.getReferenceUrls()) {
            meetingReferenceRepo.save(new MeetingReference(meeting, url));
        }

        return meeting.getId();
    }

    @Transactional
    public void updateAgendaDetails(List<MeetingDto.AgendaDetailUpdateDto> agendaList){
        for(MeetingDto.AgendaDetailUpdateDto agendaDto : agendaList){
            Agenda agenda = agendaRepo.findById(agendaDto.getAgendaId())
                    .orElseThrow(() -> new RuntimeException("Agenda not found"));
            agenda.setAgendaContent(agendaDto.getAgendaContent());
        }
    }

    @Transactional
    public void updateActionPoint(MeetingDto.ActionPointUpdateDto actionPointUpdateDto){
        Meeting meeting = meetingRepo.findById(actionPointUpdateDto.getMeetingId())
                .orElseThrow(() -> new RuntimeException("Meeting not found"));

        // 최종 요약 저장
        meeting.setMeetingLastSummary(actionPointUpdateDto.getMeetingLastSummary());

        // 액션포인트 저장
        for (MeetingDto.ActionPointDto ap : actionPointUpdateDto.getActionPoints()) {
            User user = userRepo.findById(ap.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            ActionPoint actionPoint = new ActionPoint(
                    ap.getActionContent(),
                    user,
                    ap.isFinished(),
                    meeting
            );

            actionPointRepo.save(actionPoint);
        }
    }
}
