package com.project.saas.entity.master;

import java.util.List;

public record CityResponseDto(

        Long id,

        String name,

        Long managerId,

        List<Long> serviceAreaId
) {
}
