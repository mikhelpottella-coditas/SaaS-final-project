package com.project.saas.dto.tenant.request;

import jakarta.validation.constraints.*;

public record MeterRequestDto(

        @NotBlank(message = "the type must be entered")
        String type,

        @NotNull(message = "please enter the rate per unit details")
        Double ratePerUnit,

        @NotNull(message = "enter how many photos required")
        @Max(value = 10,message = "max you can set is 10")
        @Min(value = 1,message = "least 1 is required ")
        Integer photosRequired,

        @NotNull(message = "please enter this in no.of sec. ex: 120 (for 2 mins)")
        Integer intervalBtwPhotos

) {
}
