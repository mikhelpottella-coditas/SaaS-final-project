package com.project.saas.controller.global;

import com.project.saas.dto.global.request_dto.UserRequestDto;
import com.project.saas.dto.global.responceDto.UserResponseDto;
import com.project.saas.service.global.UserCrudService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("global/user")
public class UserCrudController {

    private final UserCrudService userCrudService;

    @Operation(summary = "get the profile of the user who logged in")
    @GetMapping("/profile")
    public ResponseEntity<UserResponseDto> getProfile(){
        return ResponseEntity.ok(userCrudService.getProfile());
    }

    @Operation(summary = "update the profile of the user who logged in")
    @PatchMapping("/update")
    public ResponseEntity<String> updateProfile(@RequestBody UserRequestDto userRequestDto){
        return ResponseEntity.ok(userCrudService.updateProfile(userRequestDto));
    }



}
