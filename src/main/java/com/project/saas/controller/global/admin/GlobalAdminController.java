package com.project.saas.controller.global.admin;


import com.project.saas.dto.global.request_dto.InvitationRequestDto;
import com.project.saas.dto.global.request_dto.UserRequestDto;
import com.project.saas.dto.global.responceDto.UserResponseDto;
import com.project.saas.entity.master.User;
import com.project.saas.enums.TenantStatus;
import com.project.saas.service.InvitationService;
import com.project.saas.service.global.AdminService;
import com.project.saas.service.global.GlobalManagementService;
import com.project.saas.service.global.UserCrudService;
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



    @PostMapping("/invite/management")
    public ResponseEntity<String> inviteManagement(@RequestBody InvitationRequestDto invitationRequestDto){
        return ResponseEntity.ok(invitationService.inviteManagement(invitationRequestDto));
    }

    @GetMapping("/management")
    public ResponseEntity<List<UserResponseDto>> getAllManagement(@RequestParam(required = false,defaultValue = "0") int page,
                                                                  @RequestParam(required = false,defaultValue = "5") int size,
                                                                  @RequestParam(required = false,defaultValue = "id") String sortBy,
                                                                  @RequestParam(required = false,defaultValue = "true") boolean ascending,
                                                                  @RequestParam(required = false,defaultValue = "") String search ){
        return ResponseEntity.ok(managementService.getAllManagement(page,size,sortBy,ascending,search));
    }

    @GetMapping("/management/{id}")
    public ResponseEntity<UserResponseDto> getManagement(@PathVariable("id") Long id){
        return ResponseEntity.ok(managementService.getById(id));
    }

}
