package com.project.saas.controller.global.globalManagement;


import com.project.saas.dto.global.request_dto.StateRequestDto;
import com.project.saas.dto.global.request_dto.AssignStateRequestDto;
import com.project.saas.dto.global.request_dto.InvitationRequestDto;
import com.project.saas.dto.global.responceDto.*;
import com.project.saas.service.InvitationService;
import com.project.saas.service.global.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * this class contains the controller of the management which are only accessible by him and his higher authority
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/global/management")
public class ManagementController {

    private final InvitationService invitationService;
    private final ManagerUserService managerUserService;
    private final UserCrudService userCrudService;
    private final StateManagerService stateManagerService;
    private final StateService stateService;


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

    @DeleteMapping("/state/{id}")
    public ResponseEntity<String> deleteStateById(@PathVariable Long id){
        return ResponseEntity.ok(stateService.deleteStateById(id));
    }


    @PostMapping("/invite/sales-point")
    public ResponseEntity<String> inviteSalesPoint(@Valid @RequestBody InvitationRequestDto invitationRequestDto){
        return ResponseEntity.ok(invitationService.inviteSalesPoint(invitationRequestDto));
    }

    @DeleteMapping("/sales-point/{id}")
    public ResponseEntity<String> deleteSalesPointById(@PathVariable Long id){
        return ResponseEntity.ok(userCrudService.deleteById(id));
    }

    @PostMapping("/invite/state-manager")
    public ResponseEntity<String> inviteStateManger(@Valid @RequestBody InvitationRequestDto invitationRequestDto){
        return ResponseEntity.ok(invitationService.inviteStateManager(invitationRequestDto));
    }

    @DeleteMapping("/state-manager/{id}")
    public ResponseEntity<String> deleteStateManagerById(@PathVariable Long id){
        return ResponseEntity.ok(userCrudService.deleteById(id));
    }


}
