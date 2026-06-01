package com.project.saas.service.global;

import com.project.saas.dto.request_dto.ChangePasswordRequestDto;
import com.project.saas.dto.request_dto.UserRequestDto;
import com.project.saas.entity.master.Tenant;
import com.project.saas.entity.master.User;
import com.project.saas.entity.tenant.TenantStates;
import com.project.saas.enums.TenantStatus;
import com.project.saas.exception.CustomException;
import com.project.saas.repo.UserRepository;
import com.project.saas.service.TenantService;
import com.project.saas.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final TenantService tenantService;
    private final PasswordEncoder passwordEncoder;

    public String updateAdminProfile(Long id, UserRequestDto userRequestDto) {

        User user = userRepository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "the admin is not found to update"));

        if (userRequestDto.firstName() != null && !userRequestDto.firstName().equals(""))
            user.setFirstName(userRequestDto.firstName());
        if (userRequestDto.lastName() != null && !userRequestDto.lastName().equals(""))
            user.setLastName(userRequestDto.lastName());
        if (userRequestDto.phone() != null && !userRequestDto.phone().equals("") && userRequestDto.phone().length() != 10)
            user.setPhone(userRequestDto.phone());

        userRepository.save(user);

        log.info("update admin profile successfully with the id : {}", id);
        return "admin data updated successfully";

    }

    public String activateTenant(String tenantName, TenantStatus status) {
        Tenant tenant = tenantService.getByName(tenantName);
        tenant.setTenantStatus(status);
        tenantService.save(tenant);
        log.info("updated tenant {} successfully to active state", tenantName);
        return "updated tenant successfully to active state";
    }


    public String changePassword(ChangePasswordRequestDto changePasswordRequestDto) {
        User user = userService.findByUsername(changePasswordRequestDto.email()).orElseThrow(()-> new  CustomException(HttpStatus.NOT_FOUND, "the user is not found to change"));
        if(!passwordEncoder.matches(changePasswordRequestDto.oldPassword(), user.getPassword())) throw  new CustomException(HttpStatus.BAD_REQUEST, "wrong password !!!");
        user.setPassword(changePasswordRequestDto.newPassword());
        userRepository.save(user);
        log.info("password changed successfully for the email : {}",changePasswordRequestDto.email());
        return "password changed successfully";
    }



}
