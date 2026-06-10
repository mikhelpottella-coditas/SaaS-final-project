package com.project.saas.dto.global.request_dto;

public record MailRequestDto(
        String issuedBy,
        String issuedTo,
        String subject,
        String message
) {
}
