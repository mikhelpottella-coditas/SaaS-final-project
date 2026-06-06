package com.project.saas.dto.global.request_dto;


import com.project.saas.enums.AvailableState;
import jakarta.validation.constraints.NotBlank;

public record StateRequestDto(

        @NotBlank(message = "please enter the state in proper capital letters")
        AvailableState stateName
) {
}
