package com.project.saas.service.global;


import com.project.saas.dto.global.request_dto.UserRequestDto;
import com.project.saas.entity.master.User;
import com.project.saas.exception.CustomException;
import com.project.saas.repo.UserRepository;
import com.project.saas.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class StateManagerService {

    private final UserService userService;
    private final UserRepository userRepository;


    public String updateProfile(Long id, UserRequestDto userRequestDto) {
        User user = userService.findById(id);
        if(userRequestDto.firstName() != null) user.setFirstName(userRequestDto.firstName());
        if(userRequestDto.lastName() != null) user.setLastName(userRequestDto.lastName());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
        return "profile updated successfully";
    }



}
