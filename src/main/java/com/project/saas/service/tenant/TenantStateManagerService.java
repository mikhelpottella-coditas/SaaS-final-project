package com.project.saas.service.tenant;

import com.project.saas.entity.tenant.TenantStateManager;
import com.project.saas.repo.tenant.TenantStateManagerRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TenantStateManagerService {
    private final TenantStateManagerRepo tenantStateManagerRepo;

    public void save(TenantStateManager tenantStateManager) {
        tenantStateManagerRepo.save(tenantStateManager);
    }
}
