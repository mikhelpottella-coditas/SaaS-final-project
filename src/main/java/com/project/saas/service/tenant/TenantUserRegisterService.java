package com.project.saas.service.tenant;

import com.project.saas.dto.global.request_dto.UserRequestDto;
import com.project.saas.entity.tenant.TenantUser;
import com.project.saas.entity.tenant.TenantUserRoles;
import com.project.saas.enums.Role;
import com.project.saas.exception.CustomException;
import com.project.saas.repo.tenant.TenantUserRepo;
import com.project.saas.service.InvitationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class TenantUserRegisterService {

    private final TenantUserRepo userRepo;
    private final InvitationService invitationService;

    public String saveAdmin(@Valid UserRequestDto user, String invitation) {

        if(!invitationService.validate(user.email(), invitation)) throw  new CustomException(HttpStatus.BAD_REQUEST,"Invalid invitation");


        TenantUser tenantUser = TenantUser.builder()
                .email(user.email())
                .phone(user.phone())
                .firstName(user.firstName())
                .lastName(user.lastName())
                .password(user.password())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        TenantUserRoles userRoles = new TenantUserRoles();
        userRoles.setRole(Role.TENANT_ADMIN);

        tenantUser.addTenantUser(userRoles);
        userRepo.save(tenantUser);

        return "admin registration success";
    }
}
