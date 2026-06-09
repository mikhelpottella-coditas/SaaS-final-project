package com.project.saas.dto.tenant.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CustomerBillRequestDto(

        @NotNull(message = "please provide the customer id")
        Long customerId,

        @NotNull(message = "please give the door no.")
        String doorNo,

        @NotNull(message = "please give the meter details")
        Long meterId,

        @NotNull(message = "please enter the no. of units")
        @Min(0)
        Long units,

        @NotNull(message = "photos are empty")
        List<MeterPhotoRequestDto> meterPhotoRequestDtoList
) {
}
