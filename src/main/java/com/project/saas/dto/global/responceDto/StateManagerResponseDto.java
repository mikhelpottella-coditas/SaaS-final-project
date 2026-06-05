package com.project.saas.dto.global.responceDto;

import java.time.LocalDateTime;
import java.util.List;

public record StateManagerResponseDto (

        Long id,

        String firstName,

        String lastName,


        String email,


        String phone,

        LocalDateTime createdAt,

        LocalDateTime updatedAt,

        List<Long> assignedStatesIdList,

        boolean isAssigned
){
}
