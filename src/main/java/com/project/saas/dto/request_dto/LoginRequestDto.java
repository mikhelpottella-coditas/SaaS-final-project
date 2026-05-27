package com.project.saas.dto.request_dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginRequestDto (

        @NotNull
        @Email
        String email,

        @NotBlank
        String password

){

}
