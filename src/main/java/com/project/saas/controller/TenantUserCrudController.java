package com.project.saas.controller;

import com.project.saas.annotation.TenantValid;
import com.project.saas.dto.global.request_dto.UserRequestDto;
import com.project.saas.dto.global.responceDto.UserResponseDto;
import com.project.saas.entity.master.RefreshToken;
import com.project.saas.entity.master.User;
import com.project.saas.entity.tenant.TenantRefreshToken;
import com.project.saas.entity.tenant.TenantUser;
import com.project.saas.exception.CustomException;
import com.project.saas.repo.tenant.TenantUserRepo;
import com.project.saas.service.tenant.TenantRefreshTokenService;
import com.project.saas.service.tenant.TenantUserCurdService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Operation(summary = "to delete the refresh token by logging out")
    @DeleteMapping("/logout")
    public ResponseEntity<String> logout(){
        return ResponseEntity.ok(userCrudService.logout());
    }


}
