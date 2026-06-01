package com.project.saas.controller.global.salesPoint;

import com.project.saas.dto.request_dto.InvitationRequestDto;
import com.project.saas.enums.TenantStatus;
import com.project.saas.service.InvitationService;
import com.project.saas.service.global.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/global/sales-point")
@RequiredArgsConstructor
public class SalesPointController {

    private final InvitationService invitationService;
    private final AdminService adminService;

    @PostMapping("/invite/operational-head")
    public ResponseEntity<String> inviteOperationalHead(@Valid @RequestBody InvitationRequestDto invitationRequestDto){
        return ResponseEntity.ok(invitationService.inviteOperationHead(invitationRequestDto));
    }

    @PutMapping("/{tenantName}/{status}")
    public ResponseEntity<String> activateTenant(@PathVariable String tenantName,@PathVariable TenantStatus status) {
        return ResponseEntity.ok(adminService.activateTenant(tenantName,status));
    }




}
