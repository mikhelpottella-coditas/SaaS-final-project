package com.project.saas.controller;

import com.project.saas.annotation.TenantValid;
import com.project.saas.dto.global.request_dto.UserRequestDto;
import com.project.saas.dto.global.responceDto.UserResponseDto;
import com.project.saas.service.tenant.TenantUserCurdService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tenant/user")
@Slf4j
@TenantValid
public class TenantUserCrudController {

    private final TenantUserCurdService userCrudService;

    @Operation(summary = "get there own profile to see")
    @GetMapping("/profile")
    public ResponseEntity<UserResponseDto> getProfile(){
        return ResponseEntity.ok(userCrudService.getProfile());
    }

    @Operation(summary = "to update there own profile, accessible by anyone")
    @PatchMapping("/update")
    public ResponseEntity<String> updateProfile(@RequestBody UserRequestDto userRequestDto){
        return ResponseEntity.ok(userCrudService.updateProfile(userRequestDto));
    }


}
