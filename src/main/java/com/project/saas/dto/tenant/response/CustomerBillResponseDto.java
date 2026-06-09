package com.project.saas.dto.tenant.response;


import com.project.saas.enums.CustomerBillStatus;
import com.project.saas.enums.PaymentType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;
import java.util.List;

public record CustomerBillResponseDto(

        Long BillId,

        Long customerId,

        LocalDateTime fromDate,

        LocalDateTime toDate,

        Long meterId,

        Long units,

        Double price,

        List<String> photoUrls,

        PaymentType paymentType,

        CustomerBillStatus billStatus


) {
}
