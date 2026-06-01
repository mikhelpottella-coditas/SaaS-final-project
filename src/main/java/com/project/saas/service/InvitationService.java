package com.project.saas.service;

import com.project.saas.dto.global.request_dto.InvitationRequestDto;
import com.project.saas.entity.master.Invitation;
import com.project.saas.entity.master.User;
import com.project.saas.enums.Role;
import com.project.saas.exception.CustomException;
import com.project.saas.repo.InvitationRepo;
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
@Slf4j
@Transactional
@RequiredArgsConstructor
public class InvitationService {

    private final InvitationRepo inviteRepo;
    private final JavaMailSender javaMailSender;
    private final UserService userService;

    public String inviteUser(String issuedTo, Role role, String message, String path) {

        User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(()-> new CustomException(HttpStatus.UNAUTHORIZED," unauthorised access"));

        Invitation invite = Invitation.builder()
                .issuedAt(LocalDateTime.now())
                .issuedTo(issuedTo)
                .invitationToken(UUID.randomUUID().toString())
                .role(role)
                .issuedBy(user)
                .expiresAt(LocalDateTime.now().plusDays(2))
                .build();


        inviteRepo.save(invite);

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom("mikhel.pottella@coditas.com");
        mailMessage.setTo(issuedTo);
        mailMessage.setSubject("Invitation to on the application as a "+role.name());
        mailMessage.setText(message + "\n**this link will expire in next 48hrs \n invitation link : https://santa-disobey-washtub.ngrok-free.dev" + path + invite.getInvitationToken());

        javaMailSender.send(mailMessage);
        log.info("Invitation to on the application as a owner");
        return "invitation sent successfully";
    }


    public Boolean validate(String email, String token) {
        Invitation invite = inviteRepo.findByInvitationToken(token);
        log.info("validating the user token ");
        return email.equals(invite.getIssuedTo());
    }

    public String inviteOperationHead(@Valid InvitationRequestDto request) {
        log.info("invite operational head successfully");
        return inviteUser(request.issuedTo(), Role.OPERATIONAL_HEAD,request.message(),"/auth/register/operational-head/");
    }


    public String inviteManagement(InvitationRequestDto invitationRequestDto) {
        log.info("invite management successfully");
        return inviteUser(invitationRequestDto.issuedTo(), Role.MANAGEMENT_STAFF, invitationRequestDto.message(), "/auth/register/management-staff/");
    }


    public String inviteSalesPoint(@Valid InvitationRequestDto invitationRequestDto) {
        log.info("invite sales-point successfully");
        return inviteUser(invitationRequestDto.issuedTo(), Role.SALES_POINT, invitationRequestDto.message(), "/auth/register/sales-point/");
    }


    public String inviteStateManager(@Valid InvitationRequestDto invitationRequestDto) {
        log.info("invite to state management staff is successful");
        return inviteUser(invitationRequestDto.issuedTo(), Role.STATE_MANAGEMENT_STAFF, invitationRequestDto.message(), "/auth/register/state-management/");
    }

    public String inviteDistrictManager(@Valid InvitationRequestDto invitationRequestDto) {
        log.info("invite to state management staff is successful");
        return inviteUser(invitationRequestDto.issuedTo(), Role.DISTRICT_MANAGEMENT_STAFF, invitationRequestDto.message(), "/auth/register/state-management/");
    }

    public String inviteOperationHeadAsAdmin(InvitationRequestDto invitationRequestDto) {
        log.info("invite to admin of a provider is successful");
        return inviteUser(invitationRequestDto.issuedTo(), Role.TENANT_ADMIN, invitationRequestDto.message(), "/tenant/auth/register/admin/");

    }
}
