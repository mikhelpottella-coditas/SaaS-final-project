package com.project.saas.controller.global.globalManagement;


import com.project.saas.dto.global.request_dto.StateRequestDto;
import com.project.saas.dto.global.request_dto.AssignStateRequestDto;
import com.project.saas.dto.global.request_dto.InvitationRequestDto;
import com.project.saas.dto.global.request_dto.UserRequestDto;
import com.project.saas.dto.global.responceDto.*;
import com.project.saas.service.InvitationService;
import com.project.saas.service.TenantService;
import com.project.saas.service.global.ManagerUserService;
import com.project.saas.service.global.StateManagerService;
import com.project.saas.service.global.StateService;
import com.project.saas.service.global.UserCrudService;
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
    private final UserCrudService userCrudService;
    private final StateManagerService stateManagerService;
    private final StateService stateService;
    private final TenantService tenantService;




    @PostMapping("/createState")
    public ResponseEntity<String> createState(@RequestBody StateRequestDto stateRequestDto){
        return ResponseEntity.ok(stateService.createState(stateRequestDto));
    }

    @PostMapping("/assign-state")
    public ResponseEntity<String> assignState(@RequestBody AssignStateRequestDto assignStateRequestDto){
        return ResponseEntity.ok(stateService.assignState(assignStateRequestDto));
    }

    @PostMapping("/reassign/state-manager")
    public ResponseEntity<String> updateStateHead(@RequestBody AssignStateRequestDto assignStateRequestDto){
        return ResponseEntity.ok(stateService.updateStateHead(assignStateRequestDto));
    }

    @GetMapping("/state-managers")
    public ResponseEntity<List<StateManagerResponseDto>>  getAllStateManagers(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "5") int size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "true") boolean ascending,
            @RequestParam(required = false,defaultValue = "")  String search
    ){
        return ResponseEntity.ok(managerUserService.getAllStateManagers(page,size,sortBy,ascending,search));
    }

    @GetMapping("/state-manager/{id}")
    public ResponseEntity<StateManagerResponseDto> getStateManagerById(@PathVariable Long id){
        return ResponseEntity.ok(stateManagerService.getManagerById(id));
    }


    @GetMapping("/states")
    public ResponseEntity<List<StateResponseDto>>  getAllStates(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "5") int size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "true") boolean ascending,
            @RequestParam(required = false,defaultValue = "")  String search
    ){
        return ResponseEntity.ok(managerUserService.getAllStates(page,size,sortBy,ascending,search));
    }

    @GetMapping("/state/{id}")
    public ResponseEntity<StateResponseDto> getStateById(@PathVariable Long id){
        return ResponseEntity.ok(stateService.getStateById(id));
    }


    @PostMapping("/invite/sales-point")
    public ResponseEntity<String> inviteSalesPoint(@Valid @RequestBody InvitationRequestDto invitationRequestDto){
        return ResponseEntity.ok(invitationService.inviteSalesPoint(invitationRequestDto));
    }

    @PostMapping("/invite/state-manager")
    public ResponseEntity<String> inviteStateManger(@Valid @RequestBody InvitationRequestDto invitationRequestDto){
        return ResponseEntity.ok(invitationService.inviteStateManager(invitationRequestDto));
    }
}
