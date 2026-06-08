package com.project.saas.controller.tenant.admin;

import com.project.saas.dto.global.request_dto.AssignStateRequestDto;
import com.project.saas.dto.global.request_dto.InvitationRequestDto;
import com.project.saas.dto.global.request_dto.StateRequestDto;
import com.project.saas.dto.global.responceDto.UserResponseDto;
import com.project.saas.dto.tenant.request.MeterRequestDto;
import com.project.saas.dto.tenant.response.ManagerResponseDto;
import com.project.saas.dto.tenant.response.MeterResponseDto;
import com.project.saas.entity.tenant.TenantStates;
import com.project.saas.service.tenant.TenantInvitationService;
import com.project.saas.service.tenant.TenantManagementService;
import com.project.saas.service.tenant.TenantMeterService;
import com.project.saas.service.tenant.TenantStateService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tenant/admin")
@RequiredArgsConstructor
public class AdminController {

    private final TenantMeterService meterService;
    private final TenantManagementService managementService;
    private final TenantInvitationService invitationService;

    private final TenantStateService tenantStateService;


    // meter specific controllers

    @Operation(
            summary = "to save a new meter in a particular tenant",
            description = "this api takes Meter details as a request and then save the meter details and return you the saved details with the id"
    )
    @PostMapping("/meters")
    public ResponseEntity<MeterResponseDto> addMeter(@Valid @RequestBody MeterRequestDto meterRequestDto) {
        return ResponseEntity.status(201).body(meterService.saveMeter(meterRequestDto));
    }

    @Operation(
            summary = "to update meter in a particular tenant",
            description = "this api takes Meter details as a request and then save the meter details and return you the saved details with the id"
    )
    @PatchMapping("/meters/{id}")
    public ResponseEntity<MeterResponseDto> updateMeter(@PathVariable Long id,@Valid @RequestBody MeterRequestDto meterRequestDto) {
        return ResponseEntity.status(200).body(meterService.updateMeter(id,meterRequestDto));
    }



    @DeleteMapping("/meter/{id}")
    public ResponseEntity<String> deleteMeter(@PathVariable Long id) {
        return ResponseEntity.status(200).body(meterService.deleteMeter(id));
    }


    @GetMapping("/meters")
    public ResponseEntity<List<MeterResponseDto>> getMeters(
            @RequestParam(required = false,defaultValue = "0") int page,
            @RequestParam(required = false,defaultValue = "5") int size,
            @RequestParam(required = false,defaultValue = "id") String sortBy,
            @RequestParam(required = false,defaultValue = "true") boolean ascending,
            @RequestParam(required = false,defaultValue = "") String search
    ){
        return ResponseEntity.ok(meterService.getAllMeters(page,size,sortBy,ascending,search));
    }

    @GetMapping("/meters/{id}")
    public ResponseEntity<MeterResponseDto> getMeter(@PathVariable Long id){
        return ResponseEntity.ok(meterService.getMeterById(id));
    }



    // management staff controller


    @PostMapping("/invite/management")
    public ResponseEntity<String> inviteManagement(@RequestBody InvitationRequestDto invitationRequestDto){
        return ResponseEntity.ok(invitationService.inviteManagement(invitationRequestDto));
    }

    @GetMapping("/management")
    public ResponseEntity<List<ManagerResponseDto>> getAllManagement(@RequestParam(required = false,defaultValue = "0") int page,
                                                                     @RequestParam(required = false,defaultValue = "5") int size,
                                                                     @RequestParam(required = false,defaultValue = "id") String sortBy,
                                                                     @RequestParam(required = false,defaultValue = "true") boolean ascending,
                                                                     @RequestParam(required = false,defaultValue = "") String search ){
        return ResponseEntity.ok(managementService.getAllManagement(page,size,sortBy,ascending,search));
    }

    @GetMapping("/management/{id}")
    public ResponseEntity<ManagerResponseDto> getManagement(@PathVariable("id") Long id){
        return ResponseEntity.ok(managementService.getManagementById(id));
    }


    @DeleteMapping("/management/{id}")
    public ResponseEntity<String> deleteManagement(@PathVariable("id") Long id){
        return ResponseEntity.ok(managementService.deleteById(id));
    }


    // state controllers

    @PostMapping("/createState")
    public ResponseEntity<String> createState(@RequestBody StateRequestDto stateRequestDto){
        return ResponseEntity.ok(tenantStateService.createState(stateRequestDto));
    }

    @PostMapping("/assign-m1-state")
    public ResponseEntity<String> assignState(@RequestBody AssignStateRequestDto assignStateRequestDto){
        return ResponseEntity.ok(tenantStateService.assignState(assignStateRequestDto));
    }

    @PostMapping("/reassign-m1/state-manager")
    public ResponseEntity<String> updateStateHead(@RequestBody AssignStateRequestDto assignStateRequestDto){
        return ResponseEntity.ok(tenantStateService.assignState(assignStateRequestDto));
    }


}
