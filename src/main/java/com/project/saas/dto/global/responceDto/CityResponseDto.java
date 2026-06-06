package com.project.saas.dto.global.responceDto;

import java.util.List;

public record CityResponseDto(

        Long id,

        String name,

        Long managerId,

        List<Long> serviceAreaId
) {
}
