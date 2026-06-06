package com.project.saas.dto.global.responceDto;

import java.util.List;

public record DistrictResponseDto (

        Long id,

        String name,

        Long managerId,

        List<Long> cityIdList

){
}
