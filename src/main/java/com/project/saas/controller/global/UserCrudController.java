package com.project.saas.controller.global;

import com.project.saas.dto.global.request_dto.UserRequestDto;
import com.project.saas.dto.global.responceDto.UserResponseDto;
import com.project.saas.service.global.UserCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserCrudController {

    private final UserCrudService userCrudService;

    @GetMapping("/profile")
    public ResponseEntity<UserResponseDto> getProfile(){
        return ResponseEntity.ok(userCrudService.getProfile());
    }

    @PatchMapping("/update")
    public ResponseEntity<String> updateProfile(@RequestBody UserRequestDto userRequestDto){
        return ResponseEntity.ok(userCrudService.updateProfile(userRequestDto));
    }



}
