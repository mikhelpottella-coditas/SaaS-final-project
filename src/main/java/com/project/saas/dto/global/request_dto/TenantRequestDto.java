package com.project.saas.dto.global.request_dto;

import com.project.saas.enums.AvailableState;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record TenantRequestDto(

        @NotBlank(message = "name of the tenant is missing")
        String name,

        @NotNull(message = "please provide the subscription amount")
        Double subscriptionAmount,

        @NotNull(message = "provide the sales point id")
        Long salesPointId,

        @NotNull(message = "provide the tenant available states")
        List<AvailableState> availableStatesList

) {
}
