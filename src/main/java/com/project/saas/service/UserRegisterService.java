package com.project.saas.service;

import com.project.saas.dto.global.request_dto.UserRequestDto;
import com.project.saas.entity.master.User;
import com.project.saas.entity.master.UserRoles;
import com.project.saas.enums.Role;
import com.project.saas.exception.CustomException;
import com.project.saas.repo.global.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserRegisterService {

    private final UserRepository userRepository;
    private final InvitationService invitationService;
    private final PasswordEncoder passwordEncoder;
    private final TenantService tenantService;

    public User userBuilder(UserRequestDto userDto){
        return   User.builder()
                .email(userDto.email())
                .password(passwordEncoder.encode(userDto.password()))
                .firstName(userDto.firstName())
                .lastName(userDto.lastName())
                .phone(userDto.phone())
                .tenant(null)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }


    public String saveOperationalHead(@Valid UserRequestDto userDto, String invitation) {

        if(Boolean.FALSE.equals(invitationService.validate(userDto.email(), invitation))) throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid invitation");

       User user = userBuilder(userDto);

        UserRoles userRoles = new UserRoles();
        userRoles.setRole(Role.OPERATIONAL_HEAD);
        user.addUserRole(userRoles);

        userRepository.save(user);

        return "operational head successfully saved";
    }


    public String saveManagementStaff(@Valid UserRequestDto userDto, String invitation) {

        if(Boolean.FALSE.equals(invitationService.validate(userDto.email(), invitation))) throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid invitation");

        User user = userBuilder(userDto);

        UserRoles userRoles = new UserRoles();
        userRoles.setRole(Role.MANAGEMENT_STAFF);
        user.addUserRole(userRoles);

        userRepository.save(user);
log.info("management registration successful with the name : {}",userDto.firstName());
        return "management staff registration successfully saved";
    }

    public String saveSalesPoint(@Valid UserRequestDto userDto, String invitation) {


        if(Boolean.FALSE.equals(invitationService.validate(userDto.email(), invitation))) throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid invitation");

        User user = userBuilder(userDto);
        UserRoles userRoles = new UserRoles();
        userRoles.setRole(Role.SALES_POINT);
        user.addUserRole(userRoles);
        userRepository.save(user);
        log.info("sales point registration successful with the name : {}",userDto.firstName());
        return "sales point staff registration successfully saved";
    }

    public String saveStateManagement(@Valid UserRequestDto userDto, String invitation) {
        if(Boolean.FALSE.equals(invitationService.validate(userDto.email(), invitation))) throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid invitation");

        User user = userBuilder(userDto);

        UserRoles userRoles = new UserRoles();
        userRoles.setRole(Role.STATE_MANAGEMENT_STAFF);
        user.addUserRole(userRoles);

        userRepository.save(user);
        log.info("sales point registration successful with the name : {}",userDto.firstName());
        return "sales point staff registration successfully saved";
    }
}
