package com.project.saas.dto.global.request_dto;

import jakarta.validation.constraints.NotBlank;

public record MailRequestDto(

        @NotBlank(message = "please provide this field")
        String issuedBy,

        @NotBlank(message = "please provide this field")
        String issuedTo,

        @NotBlank(message = "please provide this field")
        String subject,

        @NotBlank(message = "please provide this field")
        String message
) {
}
