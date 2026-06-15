package com.project.saas.service.tenant;

import com.project.saas.dto.global.request_dto.UserRequestDto;
import com.project.saas.entity.master.Invitation;
import com.project.saas.entity.tenant.TenantInvitation;
import com.project.saas.entity.tenant.TenantUser;
import com.project.saas.enums.Role;
import com.project.saas.exception.CustomException;
import com.project.saas.repo.tenant.TenantUserRepo;
import com.project.saas.service.InvitationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class TenantUserRegisterService {

    private final TenantUserRepo userRepo;
    private final TenantInvitationService invitationService;
    private final InvitationService globalInvitationService;
    private final PasswordEncoder passwordEncoder;

    public String saveAdmin(@Valid UserRequestDto user) {



        TenantUser tenantUser = TenantUser.builder()
                .email(user.email())
                .phone(user.phone())
                .firstName(user.firstName())
                .lastName(user.lastName())
                .password(user.password())
                .role(Role.TENANT_ADMIN)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();


        userRepo.save(tenantUser);

        return "admin registration success";
    }

    public String register(@Valid UserRequestDto userDto, String invitation) {

        if (Boolean.FALSE.equals(invitationService.validate(userDto.email(), invitation)))
            throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid invitation");
        TenantInvitation invite = invitationService.getInvite(invitation);

        TenantUser user = TenantUser.builder()
                .firstName(userDto.firstName())
                .lastName(userDto.lastName())
                .password(passwordEncoder.encode(userDto.password()))
                .email(userDto.email())
                .phone(userDto.phone())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .role(invite.getRole())
                .build();

        user.setRole(invite.getRole());

        userRepo.save(user);
        log.info("registration successful with the name : {}", userDto.firstName());
        return "registration successful";


    }

    public String registerOperationHead(@Valid UserRequestDto userDto, String invitation) {

        if (Boolean.FALSE.equals(globalInvitationService.validate(userDto.email(), invitation)))
            throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid invitation");
        Invitation invite = globalInvitationService.getInvite(invitation);

        TenantUser user = TenantUser.builder()
                .firstName(userDto.firstName())
                .lastName(userDto.lastName())
                .password(userDto.password())
                .email(userDto.email())
                .phone(userDto.phone())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .role(invite.getRole())
                .build();

        user.setRole(invite.getRole());

        userRepo.save(user);
        log.info("registration successful with the name : {}", userDto.firstName());
        return "registration successful";

    }
}
