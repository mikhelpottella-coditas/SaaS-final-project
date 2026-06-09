package com.project.saas.dto.global.responceDto;


import com.project.saas.entity.master.Customer;
import com.project.saas.entity.master.User;
import com.project.saas.enums.ComplaintStatus;
import com.project.saas.enums.WorkType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

public record AssignWorkResponseDto(
        Long id,

        Long assignedElectricianId,

        Long customerId,

        WorkType workType,

        String workDescription,

        ComplaintStatus complaintStatus,

        LocalDateTime assignedAt
) {
}
