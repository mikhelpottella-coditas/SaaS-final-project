package com.project.saas.service.global;

import com.project.saas.dto.global.request_dto.UserRequestDto;
import com.project.saas.dto.global.responceDto.UserResponseDto;
import com.project.saas.entity.master.Tenant;
import com.project.saas.entity.master.User;
import com.project.saas.enums.TenantStatus;
import com.project.saas.exception.CustomException;
import com.project.saas.repo.global.UserRepository;
import com.project.saas.service.TenantService;
import com.project.saas.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final TenantService tenantService;
    private final PasswordEncoder passwordEncoder;



    public String activateTenant(String tenantName, TenantStatus status) {
        Tenant tenant = tenantService.getByName(tenantName);
        tenant.setTenantStatus(status);
        tenantService.save(tenant);
        log.info("updated tenant {} successfully to active state", tenantName);
        return "updated tenant successfully to active state";
    }


}
