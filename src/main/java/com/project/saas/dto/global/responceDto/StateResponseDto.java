package com.project.saas.dto.global.responceDto;


import java.util.List;

public record StateResponseDto(

        Long id,

        String name,

        Long managerId,

        List<Long>districtIdList


) {
}
