package com.project.saas.controller.tenant.management;

import com.project.saas.annotation.TenantValid;
import com.project.saas.dto.global.request_dto.AssignStateRequestDto;
import com.project.saas.dto.global.request_dto.InvitationRequestDto;
import com.project.saas.dto.tenant.response.ManagerResponseDto;
import com.project.saas.service.tenant.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/tenant/M1")
@TenantValid
public class M1ManagementController {

    private final M2ManagementService m2ManagementService;
    private final TenantInvitationService invitationService;
    private final TenantStateService tenantStateService;



    // controllers for m2 management

    @Operation(summary = "send invitation to the m2 manager")
    @PostMapping("/invite/m2")
    public ResponseEntity<String> inviteM2Management(@RequestBody InvitationRequestDto invitationRequestDto){
        return ResponseEntity.ok(invitationService.inviteM2Management(invitationRequestDto));
    }

    @Operation(summary = "get all the m2-management staff with the pagination")
    @GetMapping("/m2")
    public ResponseEntity<List<ManagerResponseDto>> getAllManagement(@RequestParam(required = false,defaultValue = "0") int page,
                                                                     @RequestParam(required = false,defaultValue = "5") int size,
                                                                     @RequestParam(required = false,defaultValue = "id") String sortBy,
                                                                     @RequestParam(required = false,defaultValue = "true") boolean ascending,
                                                                     @RequestParam(required = false,defaultValue = "") String search ){
        return ResponseEntity.ok(m2ManagementService.getAllM2Management(page,size,sortBy,ascending,search));
    }

    @Operation(summary = "get the m2 manager with the id")
    @GetMapping("/m2/{id}")
    public ResponseEntity<ManagerResponseDto> getM2Management(@PathVariable("id") Long id){
        return ResponseEntity.ok(m2ManagementService.getM2ManagementById(id));
    }


    @Operation(summary = "delete m2 manager with the id ")
    @DeleteMapping("/m2/{id}")
    public ResponseEntity<String> deleteManagement(@PathVariable("id") Long id){
        return ResponseEntity.ok(m2ManagementService.deleteById(id));
    }



// assigning the m2 manager to their states


    @Operation(summary = "assign m2 state manager to a state")
    @PostMapping("/assign-m2-state")
    public ResponseEntity<String> assignState(@RequestBody AssignStateRequestDto assignStateRequestDto){
        return ResponseEntity.ok(tenantStateService.assignStateM2(assignStateRequestDto));
    }

    @Operation(summary = "reassign m2 manager to the state")
    @PostMapping("/reassign-m2/state-manager")
    public ResponseEntity<String> updateStateHead(@RequestBody AssignStateRequestDto assignStateRequestDto){
        return ResponseEntity.ok(tenantStateService.assignStateM2(assignStateRequestDto));
    }


}
