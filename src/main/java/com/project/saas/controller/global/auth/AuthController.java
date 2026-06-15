package com.project.saas.controller.global.auth;

import com.project.saas.config.tenantConfig.TenantContext;
import com.project.saas.dto.global.request_dto.ChangePasswordRequestDto;
import com.project.saas.dto.global.request_dto.LoginRequestDto;
import com.project.saas.dto.global.request_dto.UserRequestDto;
import com.project.saas.enums.Role;
import com.project.saas.service.global.RefreshTokenService;
import com.project.saas.service.global.UserRegisterService;
import com.project.saas.service.global.UserService;
import com.project.saas.service.tenant.TenantUserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * this is auth controller for the whole application side the authorization is maintained here.
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("global/auth")
@Slf4j
public class AuthController {

    private final UserService userService;
    private final UserRegisterService userRegisterService;
    private final RefreshTokenService refreshTokenService;
    private final TenantUserService tenantUserService;

    @Operation(summary = "this is to register a user for development purpose only. seeding the user")
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRequestDto user, @RequestParam Role role){
        log.info(TenantContext.getTenant());
        return ResponseEntity.ok(tenantUserService.save(user,role));
    }

    @Operation(summary ="seeding purpose, on global side")
    @PostMapping("/global/register")
    public ResponseEntity<String> globalRegisterUser(@Valid @RequestBody UserRequestDto user,@RequestParam Role role){
        log.info(TenantContext.getTenant());
        return ResponseEntity.ok(userService.save(user,role));
    }



    @Operation(
            summary = "login to the application",
            description = "the user will login into the application by providing the user name and password and in return they get the access token and refresh token"
    )
    @PostMapping("/login")
    public ResponseEntity<String> globalLogin(@Valid @RequestBody LoginRequestDto loginRequestDto){
        log.info("trying to switch the db schema {}",TenantContext.getTenant());
        return ResponseEntity.ok(userService.validateLogin(loginRequestDto));
    }



    @Operation(summary = "to generate a new access token using the refresh token")
    @PostMapping("/refresh-token/{refreshToken}")
    public String refresh(@PathVariable String refreshToken) {
        return refreshTokenService.refresh(refreshToken);
    }


    @Operation(summary = "change the password")
    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequestDto changePasswordRequestDto) {
        return ResponseEntity.ok(userService.changePassword(changePasswordRequestDto));
    }


    @Operation(summary = "register into the application ")
    @PostMapping("/register/{invitation}")
    public ResponseEntity<String> registerWithInvitation(@Valid @RequestBody UserRequestDto user,@PathVariable String invitation){
        return  ResponseEntity.status(201).body(userRegisterService.saveUser(user,invitation));
    }



}
