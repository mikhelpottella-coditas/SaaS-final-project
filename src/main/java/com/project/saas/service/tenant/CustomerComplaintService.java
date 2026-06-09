package com.project.saas.service.tenant;


import com.project.saas.dto.tenant.response.ComplaintResponseDto;
import com.project.saas.entity.master.AssignWork;
import com.project.saas.entity.master.Customer;
import com.project.saas.entity.master.User;
import com.project.saas.entity.tenant.CustomerComplaints;
import com.project.saas.entity.tenant.TenantCustomerMeter;
import com.project.saas.entity.tenant.TenantStates;
import com.project.saas.enums.ComplaintStatus;
import com.project.saas.enums.WorkType;
import com.project.saas.exception.CustomException;
import com.project.saas.repo.tenant.CustomerComplaintsRepo;
import com.project.saas.service.global.AssignWorkService;
import com.project.saas.service.global.CustomerService;
import com.project.saas.service.global.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CustomerComplaintService {

    private final CustomerComplaintsRepo customerComplaintsRepo;
    private final UserService userService;
    private final TenantStateService tenantStateService;
    private final TenantCustomerMeterService tenantCustomerMeterService;
    private final CustomerService customerService;
    private final AssignWorkService assignWorkService;


    public List<ComplaintResponseDto>   getAllComplaintsByState(Long id, ComplaintStatus filter, int page, int size, String sortBy, boolean ascending, String search) {




        TenantStates states = tenantStateService.getById(id);

        log.info(" getting all the customers in a particular state : {} ",id);
        List<TenantCustomerMeter> customerMeterList = tenantCustomerMeterService.getAllByState(states);
        log.info("getting all the complaint in that particular state : {}",id);
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

    public CustomerComplaints getById(Long id){
        return customerComplaintsRepo.findById(id).orElseThrow(()-> new CustomException(HttpStatus.NOT_FOUND,"the complaint is not found with the given id"));
    }

    public ComplaintResponseDto getComplaintById(Long id) {
        CustomerComplaints customerComplaints = getById(id);

        log.info("getting complaint by id : {}",id);
        return new ComplaintResponseDto(customerComplaints.getId(), customerComplaints.getTenantCustomerMeter().getId(), customerComplaints.getComplaint(), customerComplaints.getComplaintStatus().name(), customerComplaints.getRaiseDate(), customerComplaints.getAssignedElectrician());
    }

    public ComplaintResponseDto assignElectrician(Long complaintId, Long electricianId) {
        CustomerComplaints customerComplaints = getById(complaintId);

        if(customerComplaints.getAssignedElectrician() != null) throw new CustomException(HttpStatus.CONFLICT,"the complaint is already assigned");
        User ele = userService.findById(electricianId);

        Customer customer = customerService.findById(customerComplaints.getTenantCustomerMeter().getCustomerId());

        customerComplaints.setAssignedElectrician(electricianId);
        customerComplaints.setComplaintStatus(ComplaintStatus.IN_PROGRESS);

        AssignWork assignWork = AssignWork.builder()
                .assignedElectrician(ele)
                .assignedAt(LocalDateTime.now())
                .workType(WorkType.COMPLAINT)
                .customer(customer)
                .complaintStatus(customerComplaints.getComplaintStatus())
                .workDescription(customerComplaints.getComplaint())
                .build();


        assignWorkService.save(assignWork);
        customerComplaintsRepo.save(customerComplaints);
        log.info("assigning complaint by id : {} to the electrician : {}",complaintId,electricianId);
        return new ComplaintResponseDto(customerComplaints.getId(), customerComplaints.getTenantCustomerMeter().getId(), customerComplaints.getComplaint(), customerComplaints.getComplaintStatus().name(), customerComplaints.getRaiseDate(), customerComplaints.getAssignedElectrician());
    }


    public ComplaintResponseDto raiseComplaint(Long id) {
        CustomerComplaints customerComplaints = getById(id);
        customerComplaints.setComplaintStatus(ComplaintStatus.RAISED_TO_M2);
        log.info("the complaint is raised to m2 manager : {}",id);
        return new ComplaintResponseDto(customerComplaints.getId(), customerComplaints.getTenantCustomerMeter().getId(), customerComplaints.getComplaint(), customerComplaints.getComplaintStatus().name(), customerComplaints.getRaiseDate(), customerComplaints.getAssignedElectrician());
    }
}
