package com.project.saas.controller.global.globalManagement;

import com.project.saas.dto.global.request_dto.DistrictRequestDto;
import com.project.saas.dto.global.request_dto.InvitationRequestDto;
import com.project.saas.dto.global.responceDto.DistrictMangerResponseDto;
import com.project.saas.dto.global.responceDto.TenantResponseDto;
import com.project.saas.dto.global.responceDto.DistrictResponseDto;
import com.project.saas.service.InvitationService;
import com.project.saas.service.global.DistrictService;
import com.project.saas.service.global.ManagerUserService;
import com.project.saas.service.global.StateService;
import com.project.saas.service.global.UserCrudService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/global/state-manager/")
public class StateManagementController{

    private final InvitationService invitationService;
    private final UserCrudService userCrudService;
    private final DistrictService districtService;
    private final StateService stateService;
    private final ManagerUserService managerUserService;


    @Operation(summary = "get all the tenants (providers) available")
    @GetMapping("/available-tenants")
    public ResponseEntity<List<TenantResponseDto>> availableStates(){
        return ResponseEntity.ok(stateService.availableTenant());
    }

    // district based controllers

    @Operation(summary = "create a new district in a state")
    @PostMapping("/create/district")
    public ResponseEntity<String> createDistrict(@Valid @RequestBody DistrictRequestDto districtRequestDto){
        return ResponseEntity.ok(districtService.createDistrict(districtRequestDto));
    }

    @Operation(summary = "delete a district head by the id")
    @DeleteMapping("/district/{id}")
    public ResponseEntity<String> deleteDistrict(@PathVariable Long id){
        return ResponseEntity.ok(districtService.deleteDistrict(id));
    }

    @Operation(summary = "get all the district with pagination")
    @GetMapping("/districts")
    public ResponseEntity<List<DistrictResponseDto>>  getAllDistricts(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "5") int size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "true") boolean ascending,
            @RequestParam(required = false,defaultValue = "")String search
    ){
        return ResponseEntity.ok(districtService.getAllDistricts(page,size,sortBy,ascending,search));
    }



    @Operation(summary = "get all the districts in a state with pagination")
    @GetMapping("/state/{stateId}/districts")
    public ResponseEntity<List<DistrictResponseDto>>  getAllDistrictsByState(
            @PathVariable Long stateId,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "5") int size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "true") boolean ascending,
            @RequestParam(required = false,defaultValue = "")String search
    ){
        return ResponseEntity.ok(districtService.getAllDistrictsByState(stateId,page,size,sortBy,ascending,search));
    }


    @Operation(summary = "get the district by the id")
    @GetMapping("/districts/{id}")
    public ResponseEntity<DistrictResponseDto> getDistrictById(@PathVariable Long id){
        return ResponseEntity.ok(districtService.getDistrictById(id));
    }


    // district manager based controllers
    @Operation(summary ="send invitation the district manager")
    @PostMapping("/invite/district-manager")
    public ResponseEntity<String> inviteDistrictManager(@Valid @RequestBody InvitationRequestDto invitationRequestDto){
        return ResponseEntity.ok(invitationService.inviteDistrictManager(invitationRequestDto));
    }


    @Operation(summary = "assign district to a district manager")
    @PatchMapping("/assign-district/{stateId}/{districtId}/district-head/{headId}")
    public ResponseEntity<String> assignDistrictHead( @PathVariable Long stateId,@PathVariable Long districtId,@PathVariable Long headId){
        return ResponseEntity.ok(districtService.assignDistrictHead(stateId,districtId,headId));
    }


    @Operation(summary = "get all the district manager with pagination")
    @GetMapping("/district-managers")
    public ResponseEntity<List<DistrictMangerResponseDto>>  getAllDistrictManagers(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "5") int size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "true") boolean ascending,
            @RequestParam(required = false,defaultValue = "")String search
    ){
        return ResponseEntity.ok(managerUserService.getAllDistrictManagers(page,size,sortBy,ascending,search));
    }


    @Operation(summary = "get all the district manger to a particular state")
    @GetMapping("/state/{stateId}/district-managers")
    public ResponseEntity<List<DistrictMangerResponseDto>> getAllDistrictManagersByState(
            @PathVariable Long stateId,
            @RequestParam(required = false,defaultValue = "0") int page,
            @RequestParam(required = false,defaultValue = "5") int size,
            @RequestParam(required = false,defaultValue = "id") String sortBy,
            @RequestParam(required = false,defaultValue = "true") boolean ascending,
            @RequestParam(required = false,defaultValue = "") String search
           ){
        return ResponseEntity.ok(districtService.getAllDistrictManagersByStateId(stateId,page,size,sortBy,ascending,search));
    }

    @Operation(summary = "get a district manager by the id")
    @GetMapping("/district-manager/{id}")
    public ResponseEntity<DistrictMangerResponseDto> getDistrictHead(@PathVariable Long id){
        return ResponseEntity.ok(districtService.getDistrictHeadById(id));
    }


    @Operation(summary = "delete a district manager by the id")
    @DeleteMapping("/district-manager/{id}")
    public ResponseEntity<String> deleteDistrictHead(@PathVariable Long id){
        return ResponseEntity.ok(userCrudService.deleteById(id));
    }








}
