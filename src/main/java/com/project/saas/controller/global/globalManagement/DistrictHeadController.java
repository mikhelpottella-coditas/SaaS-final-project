package com.project.saas.controller.global.globalManagement;

import com.project.saas.dto.global.request_dto.AssignStateRequestDto;
import com.project.saas.dto.global.request_dto.InvitationRequestDto;
import com.project.saas.dto.global.request_dto.UserRequestDto;
import com.project.saas.dto.global.responceDto.UserResponseDto;
import com.project.saas.service.InvitationService;
import com.project.saas.service.UserService;
import com.project.saas.service.global.CityService;
import com.project.saas.service.global.DistrictService;
import com.project.saas.service.global.UserCrudService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;
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

    private final UserService userService;
    private final CityService cityService;


    @PostMapping("/invite/city-manager")
    public ResponseEntity<String> inviteCityManager(@Valid @RequestBody InvitationRequestDto invitationRequestDto){
        return ResponseEntity.ok(invitationService.inviteCityManager(invitationRequestDto));
    }


    @PatchMapping("/assign/city-head")
    public ResponseEntity<String> assignState(@RequestBody AssignStateRequestDto assignStateRequestDto){
        return ResponseEntity.ok(cityService.assignCityHead(assignStateRequestDto));
    }

    @GetMapping("/city-heads")
    public ResponseEntity<List<UserResponseDto>> getAllCityHeads(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending,
            @RequestParam(defaultValue = "") String search
    ){
        return ResponseEntity.ok(districtService.getAllCityHeads(page,size,sortBy,ascending,search));
    }

    @GetMapping("/city-head/{headId}")
    public ResponseEntity<UserResponseDto> getCityHead(@PathVariable Long headId){
        return ResponseEntity.ok(districtService.getCityHeadById(headId));
    }




}
