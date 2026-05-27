package com.project.saas.controller.auth;

import com.project.saas.dto.request_dto.LoginRequestDto;
import com.project.saas.dto.request_dto.UserRequestDto;
import com.project.saas.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRequestDto user){
        return ResponseEntity.ok(userService.save(user));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDto loginRequestDto){
        return ResponseEntity.ok(userService.validateLogin(loginRequestDto));
    }

}
