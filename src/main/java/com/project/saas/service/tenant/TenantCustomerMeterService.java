package com.project.saas.service.tenant;

import com.project.saas.entity.tenant.TenantCustomerMeter;
import com.project.saas.entity.tenant.TenantStates;
import com.project.saas.repo.tenant.TenantCustomerMeterRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TenantCustomerMeterService {

    private final TenantCustomerMeterRepo tenantCustomerMeterRepo;

    public void save(TenantCustomerMeter cmt) {
        tenantCustomerMeterRepo.save(cmt);
    }

    public List<TenantCustomerMeter> getAllByState(TenantStates states) {
        return tenantCustomerMeterRepo.findAllByTenantStates(states);
    }
}
