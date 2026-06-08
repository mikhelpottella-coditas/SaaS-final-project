package com.project.saas.service.tenant;

import com.project.saas.repo.tenant.TenantMeterPhotoRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TenantMeterPhotoService {

    private final TenantMeterPhotoRepo tenantMeterPhotoRepo;

}
