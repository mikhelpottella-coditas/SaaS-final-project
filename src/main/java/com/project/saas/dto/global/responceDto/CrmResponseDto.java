package com.project.saas.dto.global.responceDto;

import java.time.LocalDateTime;

public record CrmResponseDto(
        Long id,

        String firstName,

        String lastName,


        String email,


        String phone,

        LocalDateTime createdAt,

        LocalDateTime updatedAt,

        Long cityId,

        boolean isAssigned
) {
}
