package com.project.saas.controller.global.auth;

import com.project.saas.config.tenantConfig.TenantContext;
import com.project.saas.dto.global.request_dto.ChangePasswordRequestDto;
import com.project.saas.dto.global.request_dto.LoginRequestDto;
import com.project.saas.dto.global.request_dto.UserRequestDto;
import com.project.saas.service.global.RefreshTokenService;
import com.project.saas.service.UserRegisterService;
import com.project.saas.service.UserService;
import com.project.saas.service.tenant.TenantUserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("global/auth")
@Slf4j
public class AuthController {

    private final UserService userService;
    private final UserRegisterService userRegisterService;
    private final RefreshTokenService refreshTokenService;
    private final TenantUserService tenantUserService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRequestDto user){
        log.info(TenantContext.getTenant());
        return ResponseEntity.ok(tenantUserService.save(user));
    }

    @PostMapping("/global/register")
    public ResponseEntity<String> globalRegisterUser(@Valid @RequestBody UserRequestDto user){
        log.info(TenantContext.getTenant());
        return ResponseEntity.ok(userService.save(user));
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



    @PostMapping("/refresh-token/{refreshToken}")
    public String refresh(@PathVariable String refreshToken) {
        return refreshTokenService.refresh(refreshToken);
    }


    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequestDto changePasswordRequestDto) {
        return ResponseEntity.ok(userService.changePassword(changePasswordRequestDto));
    }


    @PostMapping("/register/{invitation}")
    public ResponseEntity<String> registerWithInvitation(@Valid @RequestBody UserRequestDto user,@PathVariable String invitation){
        return  ResponseEntity.status(201).body(userRegisterService.saveUser(user,invitation));
    }


}
