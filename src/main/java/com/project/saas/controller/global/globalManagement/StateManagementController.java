package com.project.saas.controller.global.globalManagement;

import com.project.saas.dto.global.request_dto.InvitationRequestDto;
import com.project.saas.dto.global.request_dto.UserRequestDto;
import com.project.saas.dto.global.responceDto.TenantResponseDto;
import com.project.saas.dto.global.responceDto.UserResponseDto;
import com.project.saas.service.InvitationService;
import com.project.saas.service.TenantService;
import com.project.saas.service.global.DistrictService;
import com.project.saas.service.global.StateService;
import com.project.saas.service.global.UserCrudService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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


    @PostMapping("/invite/district-manager")
    public ResponseEntity<String> inviteDistrictManager(@Valid @RequestBody InvitationRequestDto invitationRequestDto){
        return ResponseEntity.ok(invitationService.inviteDistrictManager(invitationRequestDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> UpdateProfile(@PathVariable Long id,@RequestBody UserRequestDto userRequestDto){
        return ResponseEntity.ok(userCrudService.updateProfile(id,userRequestDto));
    }


    @GetMapping("/available-tenants")
    public ResponseEntity<List<TenantResponseDto>> availableStates(){
        return ResponseEntity.ok(stateService.availableTenant());
    }


    @GetMapping("/district-heads")
    public ResponseEntity<List<UserResponseDto>> getAllDistrictHeads(){
        return ResponseEntity.ok(districtService.getAllDistrictHeads());
    }

    @GetMapping("/district-head/{id}")
    public ResponseEntity<UserResponseDto> getDistrictHead(@PathVariable Long id){
        return ResponseEntity.ok(districtService.getDistrictHeadById(id));
    }

    @PatchMapping("/assign-district/{districtId}/district-head/{headId}")
    public ResponseEntity<String> assignDistrictHead(@PathVariable Long districtId,@PathVariable Long headId){
        return ResponseEntity.ok(districtService.assignDistrictHead(districtId,headId));
    }

}
