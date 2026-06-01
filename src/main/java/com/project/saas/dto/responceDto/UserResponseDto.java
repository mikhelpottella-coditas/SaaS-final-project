package com.project.saas.dto.responceDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDateTime;

public record UserResponseDto(

        Long id,

        String firstName,

        String lastName,


        String email,


        String phone,

        LocalDateTime createdAt,

        LocalDateTime updatedAt

) {
}
