package com.project.saas.service;

import com.project.saas.dto.global.request_dto.MailRequestDto;
import com.project.saas.entity.master.Invitation;
import com.project.saas.entity.master.User;
import com.project.saas.enums.Role;
import com.project.saas.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailSenderService {


    private final JavaMailSender mailSender;

    public String sendMail(MailRequestDto dto) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(dto.issuedBy());
        mailMessage.setTo(dto.issuedTo());
        mailMessage.setSubject(dto.subject());
        mailMessage.setText(dto.message());

        mailSender.send(mailMessage);
        log.info("generic mail sender to for those who are authenticated");
        return "mail sent successfully";
    }

}
