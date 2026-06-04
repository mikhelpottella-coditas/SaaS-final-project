package com.project.saas.controller.global.globalManagement;

import com.project.saas.dto.global.request_dto.AssignStateRequestDto;
import com.project.saas.dto.global.request_dto.InvitationRequestDto;
import com.project.saas.dto.global.request_dto.UserRequestDto;
import com.project.saas.dto.global.responceDto.CityMangerResponseDto;
import com.project.saas.dto.global.responceDto.UserResponseDto;
import com.project.saas.service.InvitationService;
import com.project.saas.service.UserService;
import com.project.saas.service.global.CityService;
import com.project.saas.service.global.DistrictService;
import com.project.saas.service.global.ManagerUserService;
import com.project.saas.service.global.UserCrudService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/global/district-head")
public class DistrictHeadController {

    private final InvitationService invitationService;
    private final UserCrudService userCrudService;
    private final DistrictService districtService;
    private final ManagerUserService managerUserService;

    private final UserService userService;
    private final CityService cityService;

// city manager based endpoints
    @PostMapping("/invite/city-manager")
    public ResponseEntity<String> inviteCityManager(@Valid @RequestBody InvitationRequestDto invitationRequestDto){
        return ResponseEntity.ok(invitationService.inviteCityManager(invitationRequestDto));
    }

    @PatchMapping("/assign/city-head")
    public ResponseEntity<String> assignState(@RequestBody AssignStateRequestDto assignStateRequestDto){
        return ResponseEntity.ok(cityService.assignCityHead(assignStateRequestDto));
    }

    @PatchMapping("/reassign/city-head")
    public ResponseEntity<String> reassignState(@RequestBody AssignStateRequestDto assignStateRequestDto){
        return ResponseEntity.ok(cityService.reassignCityHead(assignStateRequestDto));
    }

    @GetMapping("/all/city-heads")
    public ResponseEntity<List<CityMangerResponseDto>>  getAllCityManagers(
            @RequestParam(required = false,defaultValue = "0") int page,
            @RequestParam(required = false,defaultValue = "5") int size,
            @RequestParam(required = false,defaultValue = "id") String sortBy,
            @RequestParam(required = false,defaultValue = "true") boolean ascending,
            @RequestParam(required = false,defaultValue = "") String search
    ){
        return ResponseEntity.ok(managerUserService.getAllCityManagers(page,size,sortBy,ascending,search));
    }

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


    @GetMapping("/city-head/{headId}")
    public ResponseEntity<CityMangerResponseDto> getCityHeadById(@PathVariable Long headId){
        return ResponseEntity.ok(districtService.getCityHeadById(headId));
    }



}
