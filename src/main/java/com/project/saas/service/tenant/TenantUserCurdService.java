package com.project.saas.service.tenant;

import com.project.saas.dto.global.request_dto.UserRequestDto;
import com.project.saas.dto.global.responceDto.UserResponseDto;
import com.project.saas.entity.tenant.TenantUser;
import com.project.saas.exception.CustomException;
import com.project.saas.repo.tenant.TenantUserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TenantUserCurdService {

    private final TenantUserRepo tenantUserRepo;


    public TenantUser getById(Long id){
        return tenantUserRepo.findById(id).orElseThrow(()-> new CustomException(HttpStatus.NOT_FOUND, "the tenant user not found with the given id : "+id));
    }


    public UserResponseDto getProfile() {
        TenantUser user = tenantUserRepo.findTenantUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(user==null) throw  new CustomException(HttpStatus.NOT_FOUND, "the user is not found to update");
        return new UserResponseDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhone(), user.getCreatedAt(), user.getUpdatedAt());
    }

    public String updateProfile(UserRequestDto userRequestDto) {


        TenantUser user = tenantUserRepo.findTenantUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(user==null) throw  new CustomException(HttpStatus.NOT_FOUND, "the user is not found to update");

        if (userRequestDto.firstName() != null && !userRequestDto.firstName().isEmpty())
            user.setFirstName(userRequestDto.firstName());
        if (userRequestDto.lastName() != null && !userRequestDto.lastName().isEmpty())
            user.setLastName(userRequestDto.lastName());
        if (userRequestDto.phone() != null && userRequestDto.phone().length() == 10 && userRequestDto.phone().matches("^\\d+$"))
            user.setPhone(userRequestDto.phone());

        tenantUserRepo.save(user);

        log.info("update user profile successfully with the id : {}", user.getId());
        return "admin data updated successfully";


    }
}
