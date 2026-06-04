package com.project.saas.dto.global.responceDto;

import java.time.LocalDateTime;

public record ElectricianResponseDto(
        Long id,

        String firstName,

        String lastName,


        String email,


        String phone,

        LocalDateTime createdAt,

        LocalDateTime updatedAt,

        Long areaId,
        boolean b) {
}
