package com.project.saas.service.global;

import com.project.saas.dto.global.request_dto.AssignStateRequestDto;
import com.project.saas.dto.global.request_dto.StateRequestDto;
import com.project.saas.dto.global.responceDto.StateResponseDto;
import com.project.saas.dto.global.responceDto.TenantResponseDto;
import com.project.saas.entity.master.*;
import com.project.saas.enums.AvailableState;
import com.project.saas.enums.Role;
import com.project.saas.exception.CustomException;
import com.project.saas.repo.global.StateRepo;
import com.project.saas.repo.global.TenantAvailableStatesRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class StateService {

    private final StateRepo stateRepo;
    private final UserService userService;
    private final TenantAvailableStatesRepo availableStatesRepo;


    public String createState(StateRequestDto stateRequestDto) {

        if(stateRepo.existsByName(stateRequestDto.stateName().name())) throw new CustomException(HttpStatus.BAD_REQUEST, "the state is already exists");
        State state = State.builder().name(stateRequestDto.stateName().name()).code(stateRequestDto.stateName().getCode()).build();
        stateRepo.save(state);
        return "success on createState";
    }


    public String assignState(AssignStateRequestDto assignStateRequestDto) {
        State state = stateRepo.findStateByName(assignStateRequestDto.name()).orElseThrow(()-> new CustomException(HttpStatus.BAD_REQUEST,"the state is not available"));
        if(state.getManagerUser()!=null) throw new CustomException(HttpStatus.BAD_REQUEST,"the state manager is already assigned");
        User user = userService.findById(assignStateRequestDto.managerId());
        log.info("check the user this is state head or not");
        if(!user.getRole().equals(Role.STATE_MANAGEMENT_STAFF)) throw new CustomException(HttpStatus.FORBIDDEN,"the user is not a state head");
        state.setManagerUser(user);
        stateRepo.save(state);
        return "assigned the manager : " + user.getFirstName() + " to the state : " + assignStateRequestDto.name();
    }

    public String updateStateHead(AssignStateRequestDto assignStateRequestDto) {
        State state = stateRepo.findStateByName(assignStateRequestDto.name()).orElseThrow(()-> new CustomException(HttpStatus.BAD_REQUEST,"the state is not available"));
        User user = userService.findById(assignStateRequestDto.managerId());
        if(Objects.equals(state.getManagerUser().getId(), assignStateRequestDto.managerId())) throw new CustomException(HttpStatus.BAD_REQUEST,"the state manager you are trying to assign is the same as before");
        if(!user.getRole().equals(Role.STATE_MANAGEMENT_STAFF)) throw new CustomException(HttpStatus.FORBIDDEN,"the user is not a state head");
        state.setManagerUser(user);
        stateRepo.save(state);
        return "assigned the manager : " + user.getFirstName() + " to the state : " + assignStateRequestDto.name();

    }

    public List<TenantResponseDto> availableTenant() {
        User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(()-> new CustomException(HttpStatus.UNAUTHORIZED, "the user is not authorized"));
        State state = stateRepo.findStateByManagerUser_Id(user.getId()).orElseThrow(()-> new CustomException(HttpStatus.BAD_REQUEST,"you are not allowed!"));
        List<TenantAvailableStates> tenantAvailableStatesList = availableStatesRepo.findAllByAvailableState(AvailableState.valueOf(state.getName())).orElseThrow(()-> new CustomException(HttpStatus.NOT_FOUND, "the tenant are not there in that state"));
        log.info("fetching the all the tenants of the states availability");
        return tenantAvailableStatesList.stream().map(TenantAvailableStates::getTenant).map(tenant -> new TenantResponseDto(tenant.getId(), tenant.getName(), tenant.getSchemaName(), tenant.getTenantStatus(), tenant.getCreatedAt(), tenant.getUpdatedAt(), tenant.getSubscriptionAmount(), tenant.getOperatingTenant().getUser().getId())).toList();
    }


    public State getByStateHead(Long id) {
        return stateRepo.findStateByManagerUser_Id(id).orElseThrow(()-> new CustomException(HttpStatus.BAD_REQUEST, "the state is not under you or the is state is not available"));
    }


    public State getById(Long stateId) {
        return stateRepo.findById(stateId).orElseThrow(()-> new CustomException(HttpStatus.NOT_FOUND, "THe state is not found that you are trying to access"));
    }



    public StateResponseDto getStateById(Long id) {
        State state = getByStateHead(id);
        Long managerId = state.getManagerUser()==null?null:state.getManagerUser().getId();
        List<Long> districtIds = state.getDistrictList()==null?null:state.getDistrictList().stream().map(District::getId).toList();
        return new StateResponseDto(state.getId(), state.getName(), managerId, districtIds);
    }

    public String deleteStateById(Long id) {
        State state = getByStateHead(id);

        try {
            stateRepo.delete(state);
        } catch (Exception e) {

            throw new CustomException(HttpStatus.BAD_REQUEST, "not possible to delete the state. since few the cities are dependent on this state");
        }
        log.info("deleting the state by id : {}",id);
        return "deleted successfully";
    }
}
