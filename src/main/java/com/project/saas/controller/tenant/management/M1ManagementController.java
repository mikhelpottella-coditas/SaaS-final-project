package com.project.saas.controller.tenant.management;

import com.project.saas.dto.global.request_dto.InvitationRequestDto;
import com.project.saas.dto.global.responceDto.UserResponseDto;
import com.project.saas.dto.tenant.response.ManagerResponseDto;
import com.project.saas.service.tenant.M2ManagementService;
import com.project.saas.service.tenant.TenantInvitationService;
import com.project.saas.service.tenant.TenantManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/tenant/M1")
public class M1ManagementController {

    private final M2ManagementService m2ManagementService;
    private final TenantInvitationService invitationService;



    // controllers for m2 management

    @PostMapping("/invite/m2-management")
    public ResponseEntity<String> inviteM2Management(@RequestBody InvitationRequestDto invitationRequestDto){
        return ResponseEntity.ok(invitationService.inviteM2Management(invitationRequestDto));
    }

    @GetMapping("/m2")
    public ResponseEntity<List<ManagerResponseDto>> getAllManagement(@RequestParam(required = false,defaultValue = "0") int page,
                                                                     @RequestParam(required = false,defaultValue = "5") int size,
                                                                     @RequestParam(required = false,defaultValue = "id") String sortBy,
                                                                     @RequestParam(required = false,defaultValue = "true") boolean ascending,
                                                                     @RequestParam(required = false,defaultValue = "") String search ){
        return ResponseEntity.ok(m2ManagementService.getAllM2Management(page,size,sortBy,ascending,search));
    }

    @GetMapping("/m2/{id}")
    public ResponseEntity<ManagerResponseDto> getM2Management(@PathVariable("id") Long id){
        return ResponseEntity.ok(m2ManagementService.getM2ManagementById(id));
    }


    @DeleteMapping("/m2/{id}")
    public ResponseEntity<String> deleteManagement(@PathVariable("id") Long id){
        return ResponseEntity.ok(m2ManagementService.deleteById(id));
    }







}
