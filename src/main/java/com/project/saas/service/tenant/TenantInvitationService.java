package com.project.saas.service.tenant;

import com.project.saas.dto.global.request_dto.InvitationRequestDto;
import com.project.saas.entity.master.Invitation;
import com.project.saas.entity.master.User;
import com.project.saas.entity.tenant.TenantInvitation;
import com.project.saas.entity.tenant.TenantUser;
import com.project.saas.enums.Role;
import com.project.saas.exception.CustomException;
import com.project.saas.repo.InvitationRepo;
import com.project.saas.repo.tenant.TenantInvitationRepo;
import com.project.saas.service.global.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional

public class TenantInvitationService {

    private final TenantInvitationRepo tenantInvitationRepo;

    private final JavaMailSender javaMailSender;
    private final TenantUserService tenantUserService;
    private final  String  path = "/tenant/auth/register/";

    public String inviteUser(String issuedTo, Role role, String message, String path) {

        TenantUser user = tenantUserService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(() -> new CustomException(HttpStatus.UNAUTHORIZED, " unauthorised access"));

        TenantInvitation invite = TenantInvitation.builder()
                .issuedAt(LocalDateTime.now())
                .issuedTo(issuedTo)
                .invitationToken(UUID.randomUUID().toString())
                .role(role)
                .issuedBy(user)
                .expiresAt(LocalDateTime.now().plusDays(2))
                .build();


        tenantInvitationRepo.save(invite);

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom("mikhel.pottella@coditas.com");
        mailMessage.setTo(issuedTo);
        mailMessage.setSubject("Invitation to on the application as a " + role.name());
        mailMessage.setText(message + "\n**this link will expire in next 48hrs \n invitation link : https://santa-disobey-washtub.ngrok-free.dev" + path + invite.getInvitationToken());

        javaMailSender.send(mailMessage);
        log.info("Invitation to on the application as a owner");
        return "invitation sent successfully";
    }


    public Boolean validate(String email, String token) {
        if (token.isEmpty()) throw new CustomException(HttpStatus.BAD_REQUEST, "please share the invitation code");
        Invitation invite = tenantInvitationRepo.findByInvitationToken(token);
        log.info("validating the user token ");
        return email.equals(invite.getIssuedTo());
    }


    public Invitation getInvite(String invitation) {
        return tenantInvitationRepo.findByInvitationToken(invitation);
    }


    public String inviteManagement(InvitationRequestDto invitationRequestDto) {
        log.info("invite management m1 manager successfully");
        return inviteUser(invitationRequestDto.issuedTo(), Role.M1_MANAGER, invitationRequestDto.message(), path);
    }


    public String inviteM2Management(InvitationRequestDto invitationRequestDto) {
        log.info("invite management m2 manager successfully");
        return inviteUser(invitationRequestDto.issuedTo(), Role.M2_MANAGER, invitationRequestDto.message(), path);

    }

    public String invitePersonnel(InvitationRequestDto invitationRequestDto) {
        log.info("invite personnel successfully");
        return inviteUser(invitationRequestDto.issuedTo(), Role.PERSONNEL, invitationRequestDto.message(), path);

    }
}



