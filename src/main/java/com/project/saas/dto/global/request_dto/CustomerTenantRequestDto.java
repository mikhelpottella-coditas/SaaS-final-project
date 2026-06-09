package com.project.saas.dto.global.request_dto;

import com.project.saas.entity.master.Area;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public record CustomerTenantRequestDto(


        Long customerId,

        Long tenantId,

        Long areaId


) {
}
