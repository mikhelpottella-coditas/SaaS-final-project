package com.project.saas.controller.global;

import com.project.saas.dto.global.request_dto.InvitationRequestDto;
import com.project.saas.dto.global.responceDto.TenantResponseDto;
import com.project.saas.enums.TenantStatus;
import com.project.saas.service.InvitationService;
import com.project.saas.service.TenantService;
import com.project.saas.service.global.AdminService;
import com.project.saas.service.global.UserCrudService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/global/sales-point")
@RequiredArgsConstructor
public class SalesPointController {

    private final InvitationService invitationService;
    private final AdminService adminService;
    private final TenantService tenantService;
    private final UserCrudService userCrudService;


    @PostMapping("/invite/operational-head")
    public ResponseEntity<String> inviteOperationalHead(@Valid @RequestBody InvitationRequestDto invitationRequestDto){
        return ResponseEntity.ok(invitationService.inviteOperationHead(invitationRequestDto));
    }

    @PutMapping("/{tenantName}/{status}")
    public ResponseEntity<String> activateTenant(@PathVariable String tenantName,@PathVariable TenantStatus status) {
        return ResponseEntity.ok(adminService.activateTenant(tenantName,status));
    }


    @GetMapping("/all/tenant")
    public ResponseEntity<List<TenantResponseDto>> getAllTenants(){
        return ResponseEntity.ok(tenantService.getAll());
    }

    @GetMapping("/tenant-under-me")
    private ResponseEntity<List<TenantResponseDto>> getTenantUnderMe(){
        return ResponseEntity.ok(tenantService.getBySalesPoint());
    }

    @GetMapping("/tenant/{id}")
    public ResponseEntity<TenantResponseDto> getTenant(@PathVariable Long id){
        return ResponseEntity.ok(tenantService.getTenantRequestDtoById(id));
    }






}
