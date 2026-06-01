package com.project.saas.dto.global.responceDto;

import com.project.saas.entity.master.OperatingTenant;
import com.project.saas.enums.TenantStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

public record TenantResponseDto(

        Long id,

        String name,

        String schemaName,

        TenantStatus tenantStatus,

        LocalDateTime createdAt,
        LocalDateTime updatedAt,


        Double subscriptionAmount,

        Long operatingHeadId
) {
}
