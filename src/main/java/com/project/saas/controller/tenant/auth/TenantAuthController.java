package com.project.saas.controller.tenant.auth;

import com.project.saas.config.tenantConfig.TenantContext;
import com.project.saas.dto.global.request_dto.LoginRequestDto;
import com.project.saas.dto.global.request_dto.UserRequestDto;
import com.project.saas.service.tenant.TenantUserRegisterService;
import com.project.saas.service.tenant.TenantUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tenant/auth")
@Slf4j
public class TenantAuthController {

    private final TenantUserService tenantUserService;
    private final TenantUserRegisterService tenantUserRegisterService;
    private final TenantUserRegisterService registerService;

    @PostMapping("/register/admin/{invitation}")
    public ResponseEntity<String> adminRegister(@Valid @RequestBody UserRequestDto user, @PathVariable String invitation){
        return  ResponseEntity.status(201).body(registerService.saveAdmin(user,invitation));
    }

    @PostMapping("/register/{invitation}")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRequestDto user,@PathVariable String invitation){
        log.info(TenantContext.getTenant());
        return ResponseEntity.ok(tenantUserRegisterService.register(user,invitation));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto){
        return ResponseEntity.ok(tenantUserService.validateLogin(loginRequestDto));
    }

}
