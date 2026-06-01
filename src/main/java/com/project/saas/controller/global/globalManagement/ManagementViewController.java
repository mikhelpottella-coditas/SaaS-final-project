package com.project.saas.controller.global.globalManagement;

import com.project.saas.dto.responceDto.UserResponseDto;
import com.project.saas.service.global.ManagerUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/global/managemant")
public class ManagementViewController {

    private final ManagerUserService managerUserService;

    @GetMapping("/all/state-managers")
    public ResponseEntity<List<UserResponseDto>>  getAllStateManagers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending
    ){
        return ResponseEntity.ok(managerUserService.getAllStateManagers(page,size,sortBy,ascending));
    }

    @GetMapping("/all/district-managers")
    public ResponseEntity<List<UserResponseDto>>  getAllDistrictManagers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending
    ){
        return ResponseEntity.ok(managerUserService.getAllDistrictManagers(page,size,sortBy,ascending));
    }

    @GetMapping("/all/city-managers")
    public ResponseEntity<List<UserResponseDto>>  getAllCityManagers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending
    ){
        return ResponseEntity.ok(managerUserService.getAllCityManagers(page,size,sortBy,ascending));
    }



}
