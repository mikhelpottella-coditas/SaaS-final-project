package com.project.saas.dto.global.responceDto;

import com.project.saas.entity.master.District;
import com.project.saas.entity.master.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.List;

public record StateResponseDto(

        Long id,

        String name,

        Long managerId,

        List<Long>districtIdList


) {
}
