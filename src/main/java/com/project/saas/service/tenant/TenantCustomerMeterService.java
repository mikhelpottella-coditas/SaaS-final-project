package com.project.saas.service.tenant;

import com.project.saas.dto.tenant.request.TenantCustomerMeterRequestDto;
import com.project.saas.entity.tenant.TenantCustomerMeter;
import com.project.saas.entity.tenant.TenantMeter;
import com.project.saas.entity.tenant.TenantStates;
import com.project.saas.exception.CustomException;
import com.project.saas.repo.tenant.TenantCustomerMeterRepo;
import com.project.saas.repo.tenant.TenantMeterRepo;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TenantCustomerMeterService {

    private final TenantCustomerMeterRepo tenantCustomerMeterRepo;
    private final TenantMeterRepo tenantMeterRepo;
    private final TenantStateService tenantStateService;

    public void save(TenantCustomerMeter cmt) {
        tenantCustomerMeterRepo.save(cmt);
    }

    public List<TenantCustomerMeter> getAllByState(TenantStates states) {
        return tenantCustomerMeterRepo.findAllByTenantStates(states);
    }

    public List<TenantCustomerMeter> getAllCustomerMetersByCustomerId(Long customerId) {
        return tenantCustomerMeterRepo.findAllByCustomerId(customerId);
    }

    public String register(@Valid TenantCustomerMeterRequestDto tenantCustomerMeterRequestDto) {

        TenantMeter meter = tenantMeterRepo.findById(tenantCustomerMeterRequestDto.tenantMeterId()).orElseThrow(()-> new CustomException(HttpStatus.NOT_FOUND, "meter not found"));
        TenantStates states = tenantStateService.getById(tenantCustomerMeterRequestDto.tenantStatesId());
        TenantCustomerMeter tenantCustomerMeter = TenantCustomerMeter.builder()
                .tenantMeter(meter)
                .customerId(tenantCustomerMeterRequestDto.customerId())
                .firstName(tenantCustomerMeterRequestDto.firstName())
                .lastName(tenantCustomerMeterRequestDto.lastName())
                .email(tenantCustomerMeterRequestDto.email())
                .phone(tenantCustomerMeterRequestDto.phone())
                .address(tenantCustomerMeterRequestDto.address())
                .doorNo(tenantCustomerMeterRequestDto.doorNo())
                .tenantStates(states).build();

        tenantCustomerMeterRepo.save(tenantCustomerMeter);

        log.info("the customer registration successfully on the tenant meter with id : {}",meter.getId());
        return "the customer registration successfully on the tenant meter with id : "+meter.getId();

    }
}
