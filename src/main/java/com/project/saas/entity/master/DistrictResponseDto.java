package com.project.saas.entity.master;

import java.util.List;

public record DistrictResponseDto (

        Long id,

        String name,

        Long managerId,

        List<Long> cityIdList

){
}
