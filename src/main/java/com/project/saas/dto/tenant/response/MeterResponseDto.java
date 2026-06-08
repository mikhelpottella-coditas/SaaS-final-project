package com.project.saas.dto.tenant.response;

public record MeterResponseDto(

        Long id,

        String type,

        Double ratePerUnit,

        Integer photosRequired,

        Integer intervalBtwPhotos

) {
}
