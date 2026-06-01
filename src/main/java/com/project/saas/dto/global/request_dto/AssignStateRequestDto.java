package com.project.saas.dto.global.request_dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AssignStateRequestDto(

        @NotBlank(message = "please give the name of the city")
        String name,


        @NotBlank(message = "please give the code of the city")
        String code,

        @NotNull(message = "please give the id of the manager whom you want assign")
        Long managerId
) {
}
