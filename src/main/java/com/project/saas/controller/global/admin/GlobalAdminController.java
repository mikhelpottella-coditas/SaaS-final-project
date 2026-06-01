package com.project.saas.controller.global.admin;


import com.project.saas.dto.global.request_dto.InvitationRequestDto;
import com.project.saas.dto.global.request_dto.UserRequestDto;
import com.project.saas.dto.global.responceDto.UserResponseDto;
import com.project.saas.enums.TenantStatus;
import com.project.saas.service.InvitationService;
import com.project.saas.service.global.AdminService;
import com.project.saas.service.global.GlobalManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/global/admin/")
public class GlobalAdminController {

    private final AdminService adminService;
    private final InvitationService invitationService;
    private final GlobalManagementService managementService;




    @PutMapping("/{id}")
    public ResponseEntity<String> updateAdminProfile(@PathVariable Long id,@RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok(adminService.updateAdminProfile(id,userRequestDto));
    }



    @PutMapping("/{tenantName}/{status}")
    public ResponseEntity<String> activateTenant(@PathVariable String tenantName,@PathVariable TenantStatus status) {
        return ResponseEntity.ok(adminService.activateTenant(tenantName,status));
    }


    @PostMapping("/invite/management")
    public ResponseEntity<String> inviteManagement(@RequestBody InvitationRequestDto invitationRequestDto){
        return ResponseEntity.ok(invitationService.inviteManagement(invitationRequestDto));
    }


    @GetMapping("/all-management")
    public ResponseEntity<List<UserResponseDto>> getAllManagement(@RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "5") int size,
                                                                  @RequestParam(defaultValue = "id") String sortBy,
                                                                  @RequestParam(defaultValue = "true") boolean ascending){
        return ResponseEntity.ok(managementService.getAllManagement(page,size,sortBy,ascending));
    }



}
