package com.project.saas.controller.global.globalManagement;


import com.project.saas.dto.global.request_dto.StateRequestDto;
import com.project.saas.dto.global.request_dto.AssignStateRequestDto;
import com.project.saas.dto.global.request_dto.InvitationRequestDto;
import com.project.saas.dto.global.request_dto.UserRequestDto;
import com.project.saas.dto.global.responceDto.TenantResponseDto;
import com.project.saas.dto.global.responceDto.UserResponseDto;
import com.project.saas.service.InvitationService;
import com.project.saas.service.TenantService;
import com.project.saas.service.global.ManagerUserService;
import com.project.saas.service.global.StateManagerService;
import com.project.saas.service.global.StateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/global/management")
public class ManagementController {

    private final InvitationService invitationService;
    private final ManagerUserService managerUserService;
    private final StateManagerService stateManagerService;
    private final StateService stateService;
    private final TenantService tenantService;

    @PatchMapping("/profile/update")
    public ResponseEntity<String>  updateProfile(@RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok(managerUserService.updateProfile(userRequestDto));
    }

    @PostMapping("/createState")
    public ResponseEntity<String> createState(@RequestBody StateRequestDto stateRequestDto){
        return ResponseEntity.ok(stateService.createState(stateRequestDto));
    }

    @PostMapping("/assign-state")
    public ResponseEntity<String> assignState(@RequestBody AssignStateRequestDto assignStateRequestDto){
        return ResponseEntity.ok(stateService.assignState(assignStateRequestDto));
    }

    @GetMapping("/all/state-managers")
    public ResponseEntity<List<UserResponseDto>>  getAllStateManagers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending
    ){
        return ResponseEntity.ok(managerUserService.getAllStateManagers(page,size,sortBy,ascending));
    }

    @GetMapping("/all/district-managers")
    public ResponseEntity<List<UserResponseDto>>  getAllDistrictManagers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending
    ){
        return ResponseEntity.ok(managerUserService.getAllDistrictManagers(page,size,sortBy,ascending));
    }

    @GetMapping("/all/city-managers")
    public ResponseEntity<List<UserResponseDto>>  getAllCityManagers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending
    ){
        return ResponseEntity.ok(managerUserService.getAllCityManagers(page,size,sortBy,ascending));
    }





    @PostMapping("/invite/operational-head")
    public ResponseEntity<String> inviteOperationalHead(@Valid @RequestBody InvitationRequestDto invitationRequestDto){
        return ResponseEntity.ok(invitationService.inviteOperationHead(invitationRequestDto));
    }

    @PostMapping("/invite/sales-point")
    public ResponseEntity<String> inviteSalesPoint(@Valid @RequestBody InvitationRequestDto invitationRequestDto){
        return ResponseEntity.ok(invitationService.inviteSalesPoint(invitationRequestDto));
    }

    @PostMapping("/invite/state-manager")
    public ResponseEntity<String> inviteStateManger(@Valid @RequestBody InvitationRequestDto invitationRequestDto){
        return ResponseEntity.ok(invitationService.inviteStateManager(invitationRequestDto));
    }

    @PatchMapping("/update/state-manager/{id}")
    public ResponseEntity<String> updateStateHead(@PathVariable Long id,@RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok(stateManagerService.updateProfile(id,userRequestDto));
    }


    @GetMapping("/all/tenant")
    public ResponseEntity<List<TenantResponseDto>> getAllTenants(){
        return ResponseEntity.ok(tenantService.getAll());
    }

    @GetMapping("/tenant/{id}")
    public ResponseEntity<TenantResponseDto> getTenant(@PathVariable Long id){
        return ResponseEntity.ok(tenantService.getTenantRequestDtoById(id));
    }



}
