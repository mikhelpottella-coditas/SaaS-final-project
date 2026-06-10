package com.project.saas.dto.global.request_dto;


public record CustomerTenantRequestDto(


        Long customerId,

        Long tenantId,

        Long areaId


) {
}
