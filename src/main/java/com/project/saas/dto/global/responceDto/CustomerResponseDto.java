package com.project.saas.dto.global.responceDto;

import com.project.saas.entity.master.*;
import com.project.saas.enums.Role;
import jakarta.persistence.*;

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
