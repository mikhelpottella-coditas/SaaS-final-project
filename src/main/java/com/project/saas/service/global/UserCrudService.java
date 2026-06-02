package com.project.saas.service.global;

import com.project.saas.dto.global.request_dto.UserRequestDto;
import com.project.saas.entity.master.User;
import com.project.saas.exception.CustomException;
import com.project.saas.repo.global.UserRepository;
import com.project.saas.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserCrudService {

    private final UserService userService;
    private final UserRepository userRepository;


    public String updateProfile(Long id, UserRequestDto userRequestDto)  {

        User user = userRepository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "the admin is not found to update"));

        if (userRequestDto.firstName() != null && !userRequestDto.firstName().equals(""))
            user.setFirstName(userRequestDto.firstName());
        if (userRequestDto.lastName() != null && !userRequestDto.lastName().equals(""))
            user.setLastName(userRequestDto.lastName());
        if (userRequestDto.phone() != null && !userRequestDto.phone().equals("") && userRequestDto.phone().length() == 10 && userRequestDto.phone().matches("^\\d+$"))
            user.setPhone(userRequestDto.phone());

        userRepository.save(user);

        log.info("update admin profile successfully with the id : {}", id);
        return "admin data updated successfully";

    }


}
