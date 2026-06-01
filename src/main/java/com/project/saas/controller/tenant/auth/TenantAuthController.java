package com.project.saas.controller.tenant.auth;

import com.project.saas.dto.request_dto.LoginRequestDto;
import com.project.saas.dto.request_dto.UserRequestDto;
import com.project.saas.service.tenant.TenantUserRegisterService;
import com.project.saas.service.tenant.TenantUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tenant/auth")
public class TenantAuthController {

    private final TenantUserService tenantUserService;
    private final TenantUserRegisterService registerService;

    @PostMapping("/register/admin/{invitation}")
    public ResponseEntity<String> adminRegister(@Valid @RequestBody UserRequestDto user, @PathVariable String invitation){
        return  ResponseEntity.status(201).body(registerService.saveAdmin(user,invitation));
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto){
        return ResponseEntity.ok(tenantUserService.validateLogin(loginRequestDto));
    }

}
