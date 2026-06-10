package com.project.saas.controller.global.admin;


import com.project.saas.dto.global.request_dto.InvitationRequestDto;
import com.project.saas.dto.global.responceDto.UserResponseDto;
import com.project.saas.service.InvitationService;
import com.project.saas.service.global.AdminService;
import com.project.saas.service.global.GlobalManagementService;
import com.project.saas.service.global.UserCrudService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * this class contains the APIs about that of the admin of the application. all the APIs which are accessible only to him are found in this class
 *
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/global/admin/")
public class GlobalAdminController {

    private final AdminService adminService;
    private final InvitationService invitationService;
    private final GlobalManagementService managementService;
    private final UserCrudService userCrudService;


    @Operation(summary = "invite management staff. accessed by only admin of the application")
    @PostMapping("/invite/management")
    public ResponseEntity<String> inviteManagement(@RequestBody InvitationRequestDto invitationRequestDto){
        return ResponseEntity.ok(invitationService.inviteManagement(invitationRequestDto));
    }

    @Operation(summary = "fetch all the management staff")
    @GetMapping("/management")
    public ResponseEntity<List<UserResponseDto>> getAllManagement(@RequestParam(required = false,defaultValue = "0") int page,
                                                                  @RequestParam(required = false,defaultValue = "5") int size,
                                                                  @RequestParam(required = false,defaultValue = "id") String sortBy,
                                                                  @RequestParam(required = false,defaultValue = "true") boolean ascending,
                                                                  @RequestParam(required = false,defaultValue = "") String search ){
        return ResponseEntity.ok(managementService.getAllManagement(page,size,sortBy,ascending,search));
    }

    @Operation(summary = "get management staff by id ")
    @GetMapping("/management/{id}")
    public ResponseEntity<UserResponseDto> getManagement(@PathVariable Long id){
        return ResponseEntity.ok(managementService.getById(id));
    }

    @Operation(summary = "delete management staff by Id")
    @DeleteMapping("/management/{id}")
    public ResponseEntity<String> deleteManagement(@PathVariable Long id){
        return ResponseEntity.ok(userCrudService.deleteById(id));
    }

}
