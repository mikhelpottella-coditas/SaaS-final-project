package com.project.saas.dto.tenant.response;

import com.project.saas.entity.tenant.TenantCustomerMeter;
import com.project.saas.entity.tenant.TenantStateManager;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;

import java.util.List;

public record StateResponseDto(
        Long id,

        String name,
        String code,

        List<Long>tenantStateManagerIdList,

        List<Long>tenantCustomerMeterIdList

) {
}
