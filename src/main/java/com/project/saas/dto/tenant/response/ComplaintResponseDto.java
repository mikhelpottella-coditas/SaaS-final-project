package com.project.saas.dto.tenant.response;


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
