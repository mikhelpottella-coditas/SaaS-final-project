package com.project.saas.dto.tenant.response;


import com.project.saas.dto.tenant.request.MeterPhotoRequestDto;
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

        LocalDateTime paymentDate,

        Long meterId,

        Long units,

        Double price,

        List<MeterPhotoRequestDto> meterPhotoRequestDtoList,

        PaymentType paymentType,

        CustomerBillStatus billStatus


) {
}
