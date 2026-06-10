package com.project.saas.controller.global.globalManagement;


import com.project.saas.dto.global.request_dto.InvitationRequestDto;
import com.project.saas.dto.global.request_dto.ServiceAreaRequestDto;
import com.project.saas.dto.global.responceDto.BillerResponseDto;
import com.project.saas.dto.global.responceDto.CityMangerResponseDto;
import com.project.saas.dto.global.responceDto.CrmResponseDto;
import com.project.saas.dto.global.responceDto.ElectricianResponseDto;
import com.project.saas.service.InvitationService;
import com.project.saas.service.global.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * this class contains the controller of the city manager which are only accessible by him and his higher authority
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/global/city-manager")
public class CityManagementController {

    private final InvitationService invitationService;
    private final AreaService areaService;
    private final BillerService billerService;
    private final ElectricianService electricianService;
    private final CrmService crmService;
    private final DistrictService districtService;


    // the service area controllers
    @Operation(summary = "")
    @PostMapping("/create/service-area")
    public ResponseEntity<String> createServiceArea(@Valid @RequestBody ServiceAreaRequestDto areaRequestDto){
        return ResponseEntity.ok(areaService.createArea(areaRequestDto));
    }

    @DeleteMapping("/service-area/{areaId}")
    public ResponseEntity<String> deleteServiceArea(@PathVariable Long areaId){
        return ResponseEntity.ok(areaService.deleteArea(areaId));
    }

    @PatchMapping("/service-area/{areaId}")
    public ResponseEntity<String> updateServiceArea(@PathVariable Long areaId,@RequestBody ServiceAreaRequestDto areaRequestDto){
        return ResponseEntity.ok(areaService.updateArea(areaId,areaRequestDto));
    }


    // sending invite to the round level workers
    @PostMapping("/invite-biller")
    public ResponseEntity<String> registerBiller(@Valid @RequestBody InvitationRequestDto invitationRequestDto){
        return ResponseEntity.ok(invitationService.inviteBiller(invitationRequestDto));
    }


    @PostMapping("/invite-crm")
    public ResponseEntity<String> registerCrm(@Valid @RequestBody InvitationRequestDto invitationRequestDto){
        return ResponseEntity.ok(invitationService.inviteCrm(invitationRequestDto));
    }

    @PostMapping("/invite-electrician")
    public ResponseEntity<String> registerElectrician(@Valid @RequestBody InvitationRequestDto invitationRequestDto){
        return ResponseEntity.ok(invitationService.inviteElectrician(invitationRequestDto));
    }

    // assign ground workers to there area
    @PostMapping("/assign/biller/{billerId}/area/{areaId}")
    public ResponseEntity<String>  assignBiller(@PathVariable(name = "billerId") Long billerId, @PathVariable(name = "areaId") Long areaId){
        return ResponseEntity.ok(billerService.assignArea(billerId,areaId));
    }

    @PostMapping("/assign/electrician/{electricianId}/area/{areaId}")
    public ResponseEntity<String>  assignElectrician(@PathVariable(name = "electricianId") Long electricianId, @PathVariable(name = "areaId") Long areaId){
        return ResponseEntity.ok(electricianService.assignArea(electricianId,areaId));
    }

    @PostMapping("/assign/crm/{crmId}/area/{cityId}")
    public ResponseEntity<String>  assignCrm(@PathVariable Long crmId, @PathVariable Long cityId){
        return ResponseEntity.ok(crmService.assignCity(crmId,cityId));
    }

    // reassign the ground worker to the area
    @PatchMapping("/reassign/biller/{billerId}/area/{areaId}")
    public ResponseEntity<String>  reassignBiller(@PathVariable Long billerId, @PathVariable Long areaId){
        return ResponseEntity.ok(billerService.reassignArea(billerId,areaId));
    }

    @PatchMapping("/reassign/electrician/{electricianId}/area/{areaId}")
    public ResponseEntity<String>  reassignElectrician(@PathVariable Long electricianId, @PathVariable Long areaId){
        return ResponseEntity.ok(electricianService.reassignArea(electricianId,areaId));
    }

    @PatchMapping("/reassign/electrician/{crmId}/area/{cityId}")
    public ResponseEntity<String>  reassignCrm(@PathVariable Long crmId, @PathVariable Long cityId){
        return ResponseEntity.ok(crmService.reassignCity(crmId,cityId));
    }

    // deleting the ground level workers
    @DeleteMapping("/biller/{billerId}")
    public ResponseEntity<String> deleteBiller(@PathVariable Long billerId){
        return ResponseEntity.ok(billerService.deleteById(billerId));
    }

    @DeleteMapping("/electrician/{electricianId}")
    public ResponseEntity<String> deleteElectrician(@PathVariable Long electricianId){
        return ResponseEntity.ok(electricianService.deleteById(electricianId));
    }

    @DeleteMapping("/crm/{crmId}")
    public ResponseEntity<String> deleteCrm(@PathVariable Long crmId){
        return ResponseEntity.ok(crmService.deleteById(crmId));
    }


    // fetching billers


    @GetMapping("/billers")
    public ResponseEntity<List<BillerResponseDto>> getBillers(
            @RequestParam(required = false,defaultValue = "0") int page,
            @RequestParam(required = false,defaultValue = "5") int size,
            @RequestParam(required = false,defaultValue = "id") String sortBy,
            @RequestParam(required = false,defaultValue = "true") boolean ascending,
            @RequestParam(required = false,defaultValue = "") String search
    ){
        return ResponseEntity.ok(billerService.getAll(page,size,sortBy,ascending,search));
    }


    @GetMapping("/billers/{id}")
    public ResponseEntity<BillerResponseDto> getBillerById(@PathVariable Long id){
        return  ResponseEntity.ok(billerService.getBillerById(id));
    }

    @GetMapping("/billers/by-city/{cityId}")
    public ResponseEntity<List<BillerResponseDto>> getBillersByCity(@PathVariable Long cityId){
        return ResponseEntity.ok(billerService.getByCity(cityId));
    }

    // fetching electricians
    @GetMapping("/electrician")
    public ResponseEntity<List<ElectricianResponseDto>> getElectrician(
            @RequestParam(required = false,defaultValue = "0") int page,
            @RequestParam(required = false,defaultValue = "5") int size,
            @RequestParam(required = false,defaultValue = "id") String sortBy,
            @RequestParam(required = false,defaultValue = "true") boolean ascending,
            @RequestParam(required = false,defaultValue = "") String search
    ){
        return ResponseEntity.ok(electricianService.getAll(page,size,sortBy,ascending,search));
    }


    @Operation(
            summary = "get all the electricians working in a particular area"
    )
    @GetMapping("/area/{areaId}/electricians")
    public ResponseEntity<List<ElectricianResponseDto>> getElectricianByArea(
            @PathVariable Long areaId,
            @RequestParam(required = false,defaultValue = "0") int page,
            @RequestParam(required = false,defaultValue = "5") int size,
            @RequestParam(required = false,defaultValue = "id") String sortBy,
            @RequestParam(required = false,defaultValue = "true") boolean ascending,
            @RequestParam(required = false,defaultValue = "") String search
    ){
        return ResponseEntity.ok(electricianService.getAllByArea(areaId,page,size,sortBy,ascending,search));
    }


    @GetMapping("/electrician/{id}")
    public ResponseEntity<ElectricianResponseDto> getElectricianById(@PathVariable Long id){
        return  ResponseEntity.ok(electricianService.getElectricianById(id));
    }


    // fetching crms
    @GetMapping("/crm")
    public ResponseEntity<List<CrmResponseDto>> getCrm(
            @RequestParam(required = false,defaultValue = "0") int page,
            @RequestParam(required = false,defaultValue = "5") int size,
            @RequestParam(required = false,defaultValue = "id") String sortBy,
            @RequestParam(required = false,defaultValue = "true") boolean ascending,
            @RequestParam(required = false,defaultValue = "") String search
    ){
        return ResponseEntity.ok(crmService.getAll(page,size,sortBy,ascending,search));
    }


    @GetMapping("/crm/{id}")
    public ResponseEntity<CrmResponseDto> getCrmById(@PathVariable Long id){
        return  ResponseEntity.ok(crmService.getCrmById(id));
    }





// city controller



    @GetMapping("/city-head/{headId}")
    public ResponseEntity<CityMangerResponseDto> getCityHeadById(@PathVariable Long headId){
        return ResponseEntity.ok(districtService.getCityHeadById(headId));
    }
}
