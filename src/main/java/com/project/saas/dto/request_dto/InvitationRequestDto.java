package com.project.saas.dto.request_dto;

import com.project.saas.enums.Role;
import jakarta.validation.constraints.NotBlank;

public record InvitationRequestDto(
        @NotBlank(message = "please provide the email that you want to send email to")
        String issuedTo,


        String message

) {
}
