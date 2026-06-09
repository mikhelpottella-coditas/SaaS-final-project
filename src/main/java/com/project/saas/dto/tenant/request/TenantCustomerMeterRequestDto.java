package com.project.saas.dto.tenant.request;



public record TenantCustomerMeterRequestDto(
        Long tenantMeterId,

        Long customerId,

        String firstName,

        String lastName,

        String email,

        String phone,

        String address,

        String doorNo,

        Long tenantStatesId
) {
}
