package com.project.saas.controller.global.globalManagement;

import com.project.saas.dto.global.request_dto.DistrictRequestDto;
import com.project.saas.dto.global.request_dto.InvitationRequestDto;
import com.project.saas.dto.global.request_dto.UserRequestDto;
import com.project.saas.dto.global.responceDto.DistrictMangerResponseDto;
import com.project.saas.dto.global.responceDto.TenantResponseDto;
import com.project.saas.dto.global.responceDto.UserResponseDto;
import com.project.saas.service.InvitationService;
import com.project.saas.service.TenantService;
import com.project.saas.service.global.DistrictService;
import com.project.saas.service.global.ManagerUserService;
import com.project.saas.service.global.StateService;
import com.project.saas.service.global.UserCrudService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/global/state-manager/")
public class StateManagementController{

    private final InvitationService invitationService;
    private final UserCrudService userCrudService;
    private final TenantService tenantService;
    private final DistrictService districtService;
    private final StateService stateService;
    private final ManagerUserService managerUserService;


    @GetMapping("/available-tenants")
    public ResponseEntity<List<TenantResponseDto>> availableStates(){
        return ResponseEntity.ok(stateService.availableTenant());
    }


    @PostMapping("/invite/district-manager")
    public ResponseEntity<String> inviteDistrictManager(@Valid @RequestBody InvitationRequestDto invitationRequestDto){
        return ResponseEntity.ok(invitationService.inviteDistrictManager(invitationRequestDto));
    }

    @PostMapping("/create/district")
    public ResponseEntity<String> createDistrict(@Valid @RequestBody DistrictRequestDto districtRequestDto){
        return ResponseEntity.ok(districtService.createDistrict(districtRequestDto));
    }

    @GetMapping("/district-managers")
    public ResponseEntity<List<DistrictMangerResponseDto>>  getAllDistrictManagers(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "5") int size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "true") boolean ascending,
            @RequestParam(required = false,defaultValue = "")String search
    ){
        return ResponseEntity.ok(managerUserService.getAllDistrictManagers(page,size,sortBy,ascending,search));
    }


    @GetMapping("/state/{stateId}/district-managers")
    public ResponseEntity<List<DistrictMangerResponseDto>> getAllDistrictManagersByState(
            @PathVariable Long stateId,
            @RequestParam(required = false,defaultValue = "0") int page,
            @RequestParam(required = false,defaultValue = "5") int size,
            @RequestParam(required = false,defaultValue = "id") String sortBy,
            @RequestParam(required = false,defaultValue = "true") boolean ascending,
            @RequestParam(required = false,defaultValue = "") String search
           ){
        return ResponseEntity.ok(districtService.getAllDistrictManagersByStateId(stateId,page,size,sortBy,ascending,search));
    }

    @GetMapping("/district-head/{id}")
    public ResponseEntity<DistrictMangerResponseDto> getDistrictHead(@PathVariable Long id){
        return ResponseEntity.ok(districtService.getDistrictHeadById(id));
    }

    @PatchMapping("/assign-district/{stateId}/{districtId}/district-head/{headId}")
    public ResponseEntity<String> assignDistrictHead( @PathVariable Long stateId,@PathVariable Long districtId,@PathVariable Long headId){
        return ResponseEntity.ok(districtService.assignDistrictHead(stateId,districtId,headId));
    }



}
