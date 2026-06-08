package com.project.saas.service.tenant;

import com.project.saas.dto.global.request_dto.AssignStateRequestDto;
import com.project.saas.dto.global.request_dto.StateRequestDto;
import com.project.saas.entity.tenant.TenantStateManager;
import com.project.saas.entity.tenant.TenantStates;
import com.project.saas.entity.tenant.TenantUser;
import com.project.saas.enums.Role;
import com.project.saas.exception.CustomException;
import com.project.saas.repo.tenant.TenantStateManagerRepo;
import com.project.saas.repo.tenant.TenantStateRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TenantStateService {

    private final TenantStateRepo tenantStateRepo;
    private final TenantUserCurdService tenantUserCurdService;
    private final TenantStateManagerRepo tenantStateManagerRepo;

    public String createState(StateRequestDto stateRequestDto) {

        if (tenantStateRepo.existsByName(stateRequestDto.stateName().name()))
            throw new CustomException(HttpStatus.BAD_REQUEST, "the state is already exists");
        TenantStates state = TenantStates.builder().name(stateRequestDto.stateName().name()).code(stateRequestDto.stateName().getCode()).build();
        tenantStateRepo.save(state);
        return "success on createState";
    }


    public String assignState(AssignStateRequestDto assignStateRequestDto) {
        TenantStates state = tenantStateRepo.findTenantStatesByName(assignStateRequestDto.name()).orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, "the state is not available"));
        TenantUser m1Manager = tenantUserCurdService.getById(assignStateRequestDto.managerId());

        TenantStateManager tenantStateManager = null;

        if (tenantStateManagerRepo.existsByState(state)) tenantStateManager = tenantStateManagerRepo.findByState(state);
        else tenantStateManager = TenantStateManager.builder().state(state).build();

        log.info("check the m1Manager this is state head or not");
        if (!m1Manager.getRole().equals(Role.M1_MANAGER))
            throw new CustomException(HttpStatus.FORBIDDEN, "the m1Manager is not a state head");
        tenantStateManager.setM1Manager(m1Manager);
        tenantStateManagerRepo.save(tenantStateManager);
        return "assigned the manager : " + m1Manager.getFirstName() + " to the state : " + assignStateRequestDto.name();
    }

    public String assignStateM2(AssignStateRequestDto assignStateRequestDto) {
        TenantStates state = tenantStateRepo.findTenantStatesByName(assignStateRequestDto.name()).orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, "the state is not available"));
        TenantUser m1Manager = tenantUserCurdService.getById(assignStateRequestDto.managerId());

        TenantStateManager tenantStateManager = null;

        if (tenantStateManagerRepo.existsByState(state)) tenantStateManager = tenantStateManagerRepo.findByState(state);
        else tenantStateManager = TenantStateManager.builder().state(state).build();

        log.info("check the m1Manager this is state head or not");
        if (!m1Manager.getRole().equals(Role.M2_MANAGER))
            throw new CustomException(HttpStatus.FORBIDDEN, "the m1Manager is not a state head");
        tenantStateManager.setM1Manager(m1Manager);
        tenantStateManagerRepo.save(tenantStateManager);
        return "assigned the manager : " + m1Manager.getFirstName() + " to the state : " + assignStateRequestDto.name();
    }

    public String assignStatePersonnel(AssignStateRequestDto assignStateRequestDto) {
        TenantStates state = tenantStateRepo.findTenantStatesByName(assignStateRequestDto.name()).orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, "the state is not available"));
        TenantUser personnel = tenantUserCurdService.getById(assignStateRequestDto.managerId());

        TenantStateManager tenantStateManager = null;

        if (tenantStateManagerRepo.existsByState(state)) tenantStateManager = tenantStateManagerRepo.findByState(state);
        else tenantStateManager = TenantStateManager.builder().state(state).build();

        log.info("check the personnel this is state head or not");
        if (!personnel.getRole().equals(Role.PERSONNEL))
            throw new CustomException(HttpStatus.FORBIDDEN, "the personnel is not a state head");
        tenantStateManager.setPersonnel(personnel);
        tenantStateManagerRepo.save(tenantStateManager);
        return "assigned the manager : " + personnel.getFirstName() + " to the state : " + assignStateRequestDto.name();
    }

    public TenantStates getById(Long id) {
        return tenantStateRepo.findById(id).orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, "the state is not available"));
    }
}
