package com.project.saas.dto.request_dto;

import com.project.saas.entity.master.UserRoles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UserRequestDto(

        @NotBlank
        String firstName,

        String lastName,

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Size(message = "the password in range of 6 to 12", min =  6, max = 12)
        String password,

        @NotBlank
        String phone





) {
}
