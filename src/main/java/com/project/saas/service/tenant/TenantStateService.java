package com.project.saas.service.tenant;

import com.project.saas.dto.global.request_dto.AssignStateRequestDto;
import com.project.saas.dto.global.request_dto.StateRequestDto;
import com.project.saas.dto.tenant.response.StateResponseDto;
import com.project.saas.entity.tenant.TenantCustomerMeter;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.ArrayList;
import java.util.List;

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
        TenantStates state = TenantStates.builder()
                .name(stateRequestDto.stateName().name())
                .code(stateRequestDto.stateName().getCode()).build();
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
            throw new CustomException(HttpStatus.FORBIDDEN, "this is not m1 manager");
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

        log.info("check the user  is personnel or not");
        if (!personnel.getRole().equals(Role.PERSONNEL))
            throw new CustomException(HttpStatus.FORBIDDEN, "this is not a personnel");
        tenantStateManager.setPersonnel(personnel);
        tenantStateManagerRepo.save(tenantStateManager);
        return "assigned the manager : " + personnel.getFirstName() + " to the state : " + assignStateRequestDto.name();
    }

    public TenantStates getById(Long id) {
        return tenantStateRepo.findById(id).orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, "the state is not available"));
    }



    public String deleteStateById(Long id) {
        TenantStates states = getById(id);
        try {
            tenantStateRepo.delete(states);
        }
        catch (Exception e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "the state is not possible to delete, since there are dependent things in this application");
        }
        return "deleted the state : " + id;
    }

    public List<StateResponseDto> getAllBpos(int page, int size, String sortBy, boolean ascending, String search) {

        Sort sort = ascending? Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        List<TenantStates> states = tenantStateRepo.findAll(pageable).getContent();

        List<StateResponseDto> stateResponseDtoList = new ArrayList<>();

        states.forEach(state-> {

            List<Long> managerIds =  state.getTenantStateManagerList()==null?null:state.getTenantStateManagerList().stream().map(s->s.getM1Manager().getId()).toList();
            List<Long> customerMeterIds =  state.getTenantCustomerMeterList()==null?null:state.getTenantCustomerMeterList().stream().map(TenantCustomerMeter::getId).toList();

            stateResponseDtoList.add(new StateResponseDto(state.getId(), state.getName(), state.getCode(), managerIds,customerMeterIds ));
        });

        if(search.isEmpty()) return stateResponseDtoList;

        log.info("getting all the state BPOs");
        log.info(String.valueOf(RequestContextHolder.getRequestAttributes()));
        return stateResponseDtoList.stream().filter(s->s.name().contains(search)).toList();

    }

    public StateResponseDto getBposById(Long stateId) {
        TenantStates state = tenantStateRepo.findById(stateId).orElseThrow(()-> new CustomException(HttpStatus.NOT_FOUND, "the state bpo is not found with the given id"));

        List<Long> managerIds =  state.getTenantStateManagerList()==null?null:state.getTenantStateManagerList().stream().map(s->s.getId()).toList();
        List<Long> customerMeterIds =  state.getTenantCustomerMeterList()==null?null:state.getTenantCustomerMeterList().stream().map(m->m.getId()).toList();

        log.info("fetching the state bpo by the id");
        return new StateResponseDto(state.getId(), state.getName(), state.getCode(), managerIds,customerMeterIds );

    }
}
