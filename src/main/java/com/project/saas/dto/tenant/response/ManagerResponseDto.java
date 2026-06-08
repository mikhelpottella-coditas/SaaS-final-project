package com.project.saas.dto.tenant.response;

import java.time.LocalDateTime;
import java.util.List;

public record ManagerResponseDto(
        Long id,

        String firstName,

        String lastName,


        String email,


        String phone,

        LocalDateTime createdAt,

        LocalDateTime updatedAt,

        List<Long> stateIds,

        boolean assigned

) {
}
