package com.project.saas.dto.request_dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record InvitationRequestDto(
        @NotBlank(message = "please provide the email that you want to send email to")
        @Email(message = "email is not in the correct format")
        String issuedTo,


        String message

) {
}
