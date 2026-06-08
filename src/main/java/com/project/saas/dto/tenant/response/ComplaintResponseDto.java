package com.project.saas.dto.tenant.response;

import com.project.saas.entity.tenant.TenantCustomerMeter;
import com.project.saas.enums.ComplaintStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

public record ComplaintResponseDto(

        Long id,

        Long tenantCustomerMeterId,

        String complaint,

        String complaintStatus,

        LocalDateTime raiseDate,

        Long assignedElectrician

) {
}
