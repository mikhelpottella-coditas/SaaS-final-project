package com.project.saas.dto.global.request_dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.NumberFormat;



public record AddAddressRequestDto(


        @NotBlank
        String address,

        @NotBlank
        String doorNo,

        @NotNull
        Long areaId


) {
}
