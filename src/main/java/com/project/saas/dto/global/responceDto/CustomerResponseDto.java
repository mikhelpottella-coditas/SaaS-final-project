package com.project.saas.dto.global.responceDto;


import java.time.LocalDateTime;
import java.util.List;

public record CustomerResponseDto (

        Long CustomerId,

        String firstName,

        String lastName,

        String email,

        String phone,

        LocalDateTime createdAt,

        LocalDateTime updatedAt,

        String address,

        List<Long> areaId,

        Long crmId,

        List<Long> tenantId,


        boolean isActive


){

}
