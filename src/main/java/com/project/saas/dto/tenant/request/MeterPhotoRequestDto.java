package com.project.saas.dto.tenant.request;


import java.time.LocalDateTime;

public record MeterPhotoRequestDto(
        String photoUrl,

        String reference,

        LocalDateTime captureTime
) {
}
