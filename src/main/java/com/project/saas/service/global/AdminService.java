package com.project.saas.service.global;

import com.project.saas.entity.master.Tenant;
import com.project.saas.enums.TenantStatus;
import com.project.saas.repo.global.UserRepository;
import com.project.saas.service.TenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

    private final TenantService tenantService;



    public String activateTenant(String tenantName, TenantStatus status) {
        Tenant tenant = tenantService.getByName(tenantName);
        tenant.setTenantStatus(status);
        tenantService.save(tenant);
        log.info("updated tenant {} successfully to active state", tenantName);
        return "updated tenant successfully to active state";
    }


}
