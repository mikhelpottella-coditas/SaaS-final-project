package com.project.saas.service.global;

import com.project.saas.dto.global.request_dto.UserRequestDto;
import com.project.saas.dto.global.responceDto.UserResponseDto;
import com.project.saas.entity.master.User;
import com.project.saas.exception.CustomException;
import com.project.saas.repo.global.UserRepository;
import com.project.saas.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserCrudService {

    private final UserService userService;
    private final UserRepository userRepository;


    public String updateProfile(UserRequestDto userRequestDto)  {

        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(user==null) throw  new CustomException(HttpStatus.NOT_FOUND, "the user is not found to update");

        if (userRequestDto.firstName() != null && !userRequestDto.firstName().isEmpty())
            user.setFirstName(userRequestDto.firstName());
        if (userRequestDto.lastName() != null && !userRequestDto.lastName().isEmpty())
            user.setLastName(userRequestDto.lastName());
        if (userRequestDto.phone() != null && userRequestDto.phone().length() == 10 && userRequestDto.phone().matches("^\\d+$"))
            user.setPhone(userRequestDto.phone());

        userRepository.save(user);

        log.info("update user profile successfully with the id : {}", user.getId());
        return "admin data updated successfully";

    }


    public UserResponseDto getProfile() {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(user==null) throw  new CustomException(HttpStatus.NOT_FOUND, "the user is not found to update");
        return new UserResponseDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhone(), user.getCreatedAt(), user.getUpdatedAt());
    }
}
