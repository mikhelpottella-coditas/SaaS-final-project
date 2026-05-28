package com.project.saas.dto.request_dto;

import com.project.saas.entity.master.User;
import com.project.saas.enums.TenantStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TenantRequestDto(

        @NotBlank(message = "name of the tenant is missing")
        String name,

        @NotNull(message = "please provide the subscription amount")
        Double subscriptionAmount
) {
}
