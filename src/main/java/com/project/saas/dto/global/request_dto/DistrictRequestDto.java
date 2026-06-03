package com.project.saas.dto.global.request_dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DistrictRequestDto(

        @NotNull(message = "please enter the state id that the district belongs")
        Long stateId,

        @NotBlank(message = " enter the name of the district")
        String districtName,

        @NotBlank(message = "please enter the code of the district")
        String code
) {
}
