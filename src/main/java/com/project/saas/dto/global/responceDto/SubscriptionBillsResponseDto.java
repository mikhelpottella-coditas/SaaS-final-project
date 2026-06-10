package com.project.saas.dto.global.responceDto;

import com.project.saas.entity.master.Tenant;
import com.project.saas.enums.BillStatus;
import jakarta.persistence.*;

import java.time.LocalDate;

public record SubscriptionBillsResponseDto(

        Long id,

        Long tenantId,

        BillStatus billStatus,

        Double amount,

        LocalDate paidDate,

        LocalDate startDate,

        LocalDate endDate

) {
}
