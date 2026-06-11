package com.project.saas.controller.tenant.auth;

import com.project.saas.annotation.TenantValid;
import com.project.saas.config.tenantConfig.TenantContext;
import com.project.saas.dto.global.request_dto.LoginRequestDto;
import com.project.saas.dto.global.request_dto.UserRequestDto;
import com.project.saas.service.tenant.TenantRefreshTokenService;
import com.project.saas.service.tenant.TenantUserRegisterService;
import com.project.saas.service.tenant.TenantUserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tenant/auth")
@Slf4j
@TenantValid
public class TenantAuthController {

    private final TenantUserService tenantUserService;
    private final TenantUserRegisterService tenantUserRegisterService;
    private final TenantUserRegisterService registerService;
    private final TenantRefreshTokenService tenantRefreshTokenService;

    @Operation(summary = "register as a admin ")
    @PostMapping("/register/admin")
    public ResponseEntity<String> adminRegister(@Valid @RequestBody UserRequestDto user){
        return  ResponseEntity.status(201).body(registerService.saveAdmin(user));
    }

    @Operation(summary = "register through invitation")
    @PostMapping("/register/{invitation}")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRequestDto user,@PathVariable String invitation){
        log.info(TenantContext.getTenant());
        return ResponseEntity.ok(tenantUserRegisterService.register(user,invitation));
    }

    @Operation(summary = "register operation head through invitation")
    @PostMapping("/register/operation-head/{invitation}")
    public ResponseEntity<String> registerOperationHeadUser(@Valid @RequestBody UserRequestDto user,@PathVariable String invitation){
        log.info(TenantContext.getTenant());
        return ResponseEntity.ok(tenantUserRegisterService.registerOperationHead(user,invitation));
    }

    @Operation(summary = "login as a user")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto){
        return ResponseEntity.ok(tenantUserService.validateLogin(loginRequestDto));
    }

    @Operation(summary = "to generate a new access token using the refresh token")
    @PostMapping("/refresh-token/{refreshToken}")
    public String refresh(@PathVariable String refreshToken) {
        return tenantRefreshTokenService.refresh(refreshToken);
    }

}
