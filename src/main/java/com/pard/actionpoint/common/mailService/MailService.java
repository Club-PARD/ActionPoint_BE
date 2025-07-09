package com.pard.actionpoint.common.mailService;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    public void sendWelcomeEmail(String toEmail, String userName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("🎉 ActionPoint 가입을 축하드립니다!");
        message.setText("안녕하세요 " + userName + "님,\n\nActionPoint에 가입해주셔서 감사합니다.\n\n더욱 가치있는 회의를 응원합니다!\n\n- ActionPoint 팀");

        mailSender.send(message);
    }
}