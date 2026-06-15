package com.project.saas.controller.global.globalManagement;

import com.project.saas.dto.global.request_dto.AssignStateRequestDto;
import com.project.saas.dto.global.request_dto.CityRequestDto;
import com.project.saas.dto.global.request_dto.DistrictRequestDto;
import com.project.saas.dto.global.request_dto.InvitationRequestDto;
import com.project.saas.dto.global.responceDto.CityMangerResponseDto;
import com.project.saas.dto.global.responceDto.CityResponseDto;
import com.project.saas.service.InvitationService;
import com.project.saas.service.global.UserService;
import com.project.saas.service.global.CityService;
import com.project.saas.service.global.DistrictService;
import com.project.saas.service.global.ManagerUserService;
import com.project.saas.service.global.UserCrudService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * this class contains the controller of the district manager which are only accessible by him and his higher authority
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/global/district-manager")
public class DistrictHeadController {

    private final InvitationService invitationService;
    private final DistrictService districtService;
    private final ManagerUserService managerUserService;

    private final CityService cityService;

    // create city
    @Operation(summary = "create a new city in a state")
    @PostMapping("/create/city")
    public ResponseEntity<String> createDistrict(@Valid @RequestBody CityRequestDto cityRequestDto){
        return ResponseEntity.ok(cityService.createCity(cityRequestDto));
    }


// city manager based endpoints
    @Operation(summary = "send invitation tot he city head")
    @PostMapping("/invite/city-manager")
    public ResponseEntity<String> inviteCityManager(@Valid @RequestBody InvitationRequestDto invitationRequestDto){
        return ResponseEntity.ok(invitationService.inviteCityManager(invitationRequestDto));
    }

    @Operation(summary = "assign city to the city head ")
    @PatchMapping("/assign/city-head")
    public ResponseEntity<String> assignState(@RequestBody AssignStateRequestDto assignStateRequestDto){
        return ResponseEntity.ok(cityService.assignCityHead(assignStateRequestDto));
    }

    @Operation(summary = "reassign city to the city head")
    @PatchMapping("/reassign/city-head")
    public ResponseEntity<String> reassignState(@RequestBody AssignStateRequestDto assignStateRequestDto){
        return ResponseEntity.ok(cityService.reassignCityHead(assignStateRequestDto));
    }

    @Operation(summary = "get all the city head with pagination")
    @GetMapping("/city-heads")
    public ResponseEntity<List<CityMangerResponseDto>>  getAllCityManagers(
            @RequestParam(required = false,defaultValue = "0") int page,
            @RequestParam(required = false,defaultValue = "5") int size,
            @RequestParam(required = false,defaultValue = "id") String sortBy,
            @RequestParam(required = false,defaultValue = "true") boolean ascending,
            @RequestParam(required = false,defaultValue = "") String search
    ){
        return ResponseEntity.ok(managerUserService.getAllCityManagers(page,size,sortBy,ascending,search));
    }

    @Operation(summary = "get all the assigned city heads")
    @GetMapping("/city-heads/assigned")
    public ResponseEntity<List<CityMangerResponseDto>> getAllCityHeadsAssigned(
            @RequestParam(required = false,defaultValue = "0") int page,
            @RequestParam(required = false,defaultValue = "5") int size,
            @RequestParam(required = false,defaultValue = "id") String sortBy,
            @RequestParam(required = false,defaultValue = "true") boolean ascending,
            @RequestParam(required = false,defaultValue = "") String search
    ){
        return ResponseEntity.ok(districtService.getAllCityHeadsAssigned(page,size,sortBy,ascending,search));
    }

    @Operation(summary = "get all the unassigned city heads")
    @GetMapping("/city-heads/unassigned")
    public ResponseEntity<List<CityMangerResponseDto>> getAllCityHeadsUnassigned(
        @RequestParam(required = false,defaultValue = "0") int page,
        @RequestParam(required = false,defaultValue = "5") int size,
        @RequestParam(required = false,defaultValue = "id") String sortBy,
        @RequestParam(required = false,defaultValue = "true") boolean ascending,
        @RequestParam(required = false,defaultValue = "") String search)
    {
        return ResponseEntity.ok(cityService.getCityHeadsUnassigned(page,size,sortBy,ascending,search));
    }




    @Operation(summary = "get all the city heads in a particular districts")
    @GetMapping("/district/{districtId}/city-heads")
    public ResponseEntity<List<CityMangerResponseDto>> getAllCityHeadsByDistricts(
            @PathVariable Long districtId,
            @RequestParam(required = false,defaultValue = "0") int page,
            @RequestParam(required = false,defaultValue = "5") int size,
            @RequestParam(required = false,defaultValue = "id") String sortBy,
            @RequestParam(required = false,defaultValue = "true") boolean ascending,
            @RequestParam(required = false,defaultValue = "") String search)
    {
        return ResponseEntity.ok(cityService.getCityHeadsByDistrict(districtId,page,size,sortBy,ascending,search));
    }

// city based controllers

    @Operation(summary = "get all the cities with pagination")
    @GetMapping("/cities")
    public ResponseEntity<List<CityResponseDto>> getAllCities(
            @RequestParam(required = false,defaultValue = "0") int page,
            @RequestParam(required = false,defaultValue = "5") int size,
            @RequestParam(required = false,defaultValue = "id") String sortBy,
            @RequestParam(required = false,defaultValue = "true") boolean ascending,
            @RequestParam(required = false,defaultValue = "") String search
    ){
        return ResponseEntity.ok(cityService.getAllCities(page,size,sortBy,ascending,search));
    }


    @Operation(summary = "get all the cities to the particular districts with pagination")
    @GetMapping("/district/{districtId}/cities")
    public ResponseEntity<List<CityResponseDto>> getAllCitiesByDistrict(
            @PathVariable Long districtId,
            @RequestParam(required = false,defaultValue = "0") int page,
            @RequestParam(required = false,defaultValue = "5") int size,
            @RequestParam(required = false,defaultValue = "id") String sortBy,
            @RequestParam(required = false,defaultValue = "true") boolean ascending,
            @RequestParam(required = false,defaultValue = "") String search
    ){
        return ResponseEntity.ok(cityService.getAllCitiesByDistrict(districtId,page,size,sortBy,ascending,search));
    }


    @Operation(summary = "get the city by id")
    @GetMapping("/cities/{id}")
    public ResponseEntity<CityResponseDto> getCityById(@PathVariable Long id){
        return ResponseEntity.ok(cityService.getCityById(id));
    }


    @Operation(summary = "delete city by id")
    @DeleteMapping("/cities/{id}")
    public ResponseEntity<String > deleteCityById(@PathVariable Long id){
        return ResponseEntity.ok(cityService.deleteCityById(id));
    }



    @Operation(summary = "delete city head by id")
    @DeleteMapping("/city-head/{headId}")
    public ResponseEntity<String> deleteCityHeadById(@PathVariable Long headId){
        return ResponseEntity.ok(districtService.deleteCityHeadById(headId));
    }

}
