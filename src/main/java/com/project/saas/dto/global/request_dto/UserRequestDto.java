package com.project.saas.dto.global.request_dto;

import com.project.saas.entity.master.UserRoles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.NumberFormat;

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
        @Size(min = 10,max = 10,message = "this is not  a valid mobile number")
        @NumberFormat(style = NumberFormat.Style.NUMBER)
        String phone





) {
}
