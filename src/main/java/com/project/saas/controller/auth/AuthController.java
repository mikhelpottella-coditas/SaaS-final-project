package com.project.saas.controller.auth;

import com.project.saas.dto.request_dto.LoginRequestDto;
import com.project.saas.dto.request_dto.UserRequestDto;
import com.project.saas.service.RefreshTokenService;
import com.project.saas.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRequestDto user){
        return ResponseEntity.ok(userService.save(user));
    }

    @Operation(
            summary = "login to the application",
            description = "the user will login into the application by providing the user name and password and in return they get the access token and refresh token"
    )
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDto loginRequestDto){
        return ResponseEntity.ok(userService.validateLogin(loginRequestDto));
    }

    @Operation(
            summary = "registration for operation head",
            description = "when the invitation sent to the operation head he can register to the application by providing the details"
    )
    @PostMapping("/register/operational-head")
    public ResponseEntity<String> operationalHeadRegister(@Valid @RequestBody UserRequestDto user){
        return ResponseEntity.ok(userService.saveOperationalHead(user));
    }


    @PostMapping("/refresh-token/{refreshToken}")
    public String refresh(@PathVariable String refreshToken) {
        return refreshTokenService.refresh(refreshToken);
    }



}
