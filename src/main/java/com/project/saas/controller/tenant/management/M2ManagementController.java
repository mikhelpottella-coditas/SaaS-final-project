package com.project.saas.controller.tenant.management;

import com.project.saas.dto.global.request_dto.AssignStateRequestDto;
import com.project.saas.dto.global.request_dto.InvitationRequestDto;
import com.project.saas.dto.tenant.response.ManagerResponseDto;
import com.project.saas.service.tenant.PersonnelService;
import com.project.saas.service.tenant.TenantInvitationService;
import com.project.saas.service.tenant.TenantStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tenant/m2")
public class M2ManagementController {

    private final TenantInvitationService invitationService;
    private final PersonnelService personnelService;
    private final TenantStateService tenantStateService;

    // controllers for m2 management

    @PostMapping("/invite/personnel")
    public ResponseEntity<String> invitePersonnel(@RequestBody InvitationRequestDto invitationRequestDto){
        return ResponseEntity.ok(invitationService.invitePersonnel(invitationRequestDto));
    }

    @GetMapping("/personnel")
    public ResponseEntity<List<ManagerResponseDto>> getAllPersonnel(@RequestParam(required = false,defaultValue = "0") int page,
                                                                     @RequestParam(required = false,defaultValue = "5") int size,
                                                                     @RequestParam(required = false,defaultValue = "id") String sortBy,
                                                                     @RequestParam(required = false,defaultValue = "true") boolean ascending,
                                                                     @RequestParam(required = false,defaultValue = "") String search ){
        return ResponseEntity.ok(personnelService.getAllPersonnel(page,size,sortBy,ascending,search));
    }

    @GetMapping("/personnel/{id}")
    public ResponseEntity<ManagerResponseDto> getPersonnel(@PathVariable("id") Long id){
        return ResponseEntity.ok(personnelService.getPersonnelById(id));
    }


    @DeleteMapping("/personnel/{id}")
    public ResponseEntity<String> deletePersonnel(@PathVariable("id") Long id){
        return ResponseEntity.ok(personnelService.deleteById(id));
    }



// assigning the personnel manager to their states


    @PostMapping("/assign-m2-state")
    public ResponseEntity<String> assignState(@RequestBody AssignStateRequestDto assignStateRequestDto){
        return ResponseEntity.ok(tenantStateService.assignStatePersonnel(assignStateRequestDto));
    }

    @PostMapping("/reassign-m2/state-manager")
    public ResponseEntity<String> updateStateHead(@RequestBody AssignStateRequestDto assignStateRequestDto){
        return ResponseEntity.ok(tenantStateService.assignStatePersonnel(assignStateRequestDto));
    }


}
