package com.project.saas.service.tenant;


import com.project.saas.dto.tenant.response.ComplaintResponseDto;
import com.project.saas.entity.tenant.CustomerComplaints;
import com.project.saas.entity.tenant.TenantCustomerMeter;
import com.project.saas.entity.tenant.TenantStates;
import com.project.saas.enums.ComplaintStatus;
import com.project.saas.repo.tenant.CustomerComplaintsRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CustomerComplaintService {

    private final CustomerComplaintsRepo customerComplaintsRepo;
    private final TenantStateService tenantStateService;
    private final TenantCustomerMeterService tenantCustomerMeterService;


    public List<ComplaintResponseDto> getAllComplaintsByState(Long id, ComplaintStatus filter) {

        TenantStates states = tenantStateService.getById(id);

        List<TenantCustomerMeter> customerMeterList = tenantCustomerMeterService.getAllByState(states);

        List<CustomerComplaints> customerComplaintsList = customerMeterList.stream().map(TenantCustomerMeter::getCustomerComplaintsList).flatMap(Collection::stream).toList();

        List<ComplaintResponseDto> complaintResponseDtoList = new ArrayList<>();

        log.info("getting all the complaints which are not null ");
        customerComplaintsList.forEach(customerComplaints -> {
            if (customerComplaints != null) {
                complaintResponseDtoList.add(new ComplaintResponseDto(customerComplaints.getId(), customerComplaints.getTenantCustomerMeter().getId(), customerComplaints.getComplaint(), customerComplaints.getComplaintStatus().name(), customerComplaints.getRaiseDate(), customerComplaints.getAssignedElectrician()));
            }
        });

        if (filter == null) return complaintResponseDtoList;
        log.info(" returning the complaints of a state which are filtered by : {}",filter.name());
        return complaintResponseDtoList.stream().filter(c -> c.complaintStatus().equals(filter.name())).toList();
    }
}
