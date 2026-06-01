package com.project.saas.dto.global.request_dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ChangePasswordRequestDto(

        @NotBlank
        @Email(message = "please provide the proper email")
        String email,

        @NotBlank(message = "please provide the old password")
        String oldPassword,

        @NotBlank(message = "please provide the new password")
        String newPassword
) {
}
