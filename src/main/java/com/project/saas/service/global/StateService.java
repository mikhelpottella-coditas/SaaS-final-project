package com.project.saas.service.global;

import com.project.saas.dto.global.request_dto.AssignStateRequestDto;
import com.project.saas.dto.global.request_dto.StateRequestDto;
import com.project.saas.dto.global.responceDto.TenantResponseDto;
import com.project.saas.entity.master.State;
import com.project.saas.entity.master.Tenant;
import com.project.saas.entity.master.User;
import com.project.saas.enums.AvailableState;
import com.project.saas.enums.Role;
import com.project.saas.exception.CustomException;
import com.project.saas.repo.global.StateRepo;
import com.project.saas.repo.global.TenantAvailableStatesRepo;
import com.project.saas.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StateService {

    private final StateRepo stateRepo;
    private final UserService userService;
    private final TenantAvailableStatesRepo availableStatesRepo;


    public String createState(StateRequestDto stateRequestDto) {
        State state = State.builder().name(stateRequestDto.stateName().name()).code(stateRequestDto.stateName().getCode()).build();
        stateRepo.save(state);
        return "success on createState";
    }


    public String assignState(AssignStateRequestDto assignStateRequestDto) {

        State state = stateRepo.findStateByName(assignStateRequestDto.name()).orElseThrow(()-> new CustomException(HttpStatus.BAD_REQUEST,"the state is not available"));
        User user = userService.findById(assignStateRequestDto.managerId());
        state.setManagerUser(user);
        stateRepo.save(state);
        return "assigned the manager : " + user.getFirstName() + " to the state : " + assignStateRequestDto.name();
    }


    public List<TenantResponseDto> availableTenant() {
        User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(()-> new CustomException(HttpStatus.UNAUTHORIZED, "the user is not authorized"));
        State state = stateRepo.findStateByManagerUser_Id(user.getId()).orElseThrow(()-> new CustomException(HttpStatus.BAD_REQUEST,"you are not allowed!"));
        List<Tenant> tenantList = availableStatesRepo.findByAvailableState(AvailableState.valueOf(state.getName())).orElseThrow(()-> new CustomException(HttpStatus.NOT_FOUND, "the tenant are not there in that state"));
        return tenantList.stream().map(tenant -> new TenantResponseDto(tenant.getId(), tenant.getName(), tenant.getSchemaName(), tenant.getTenantStatus(), tenant.getCreatedAt(), tenant.getUpdatedAt(), tenant.getSubscriptionAmount(), tenant.getOperatingTenant().getUser().getId())).toList();
    }


    public State getByStateHead(Long id) {
        return stateRepo.findStateByManagerUser_Id(id).orElseThrow(()-> new CustomException(HttpStatus.BAD_REQUEST, "the state is not under you or the is state is not available"));
    }



}
