package com.project.saas.service.global;

import com.project.saas.dto.global.request_dto.UserRequestDto;
import com.project.saas.dto.global.responceDto.UserResponseDto;
import com.project.saas.entity.master.User;
import com.project.saas.enums.Role;
import com.project.saas.exception.CustomException;
import com.project.saas.repo.UserRepository;
import com.project.saas.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ManagerUserService {


    private final UserRepository userRepository;

    private final UserService userService;

    public List<UserResponseDto> getAllUsers(int page, int size, String sortBy, boolean ascending,Role role) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        List<User> userList = userService.getByRole(role,pageable);
        log.info("getting all the user who belong to the particular role");
        return userList.stream().map(user-> new UserResponseDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhone(), user.getCreatedAt(), user.getUpdatedAt())).toList();
    }


    public List<UserResponseDto> getAllStateManagers(int page, int size, String sortBy, boolean ascending) {
        log.info("getting all the user who belong to the particular role State manager");
        return getAllUsers(page, size, sortBy, ascending, Role.STATE_MANAGEMENT_STAFF);
    }

    public List<UserResponseDto> getAllDistrictManagers(int page, int size, String sortBy, boolean ascending) {
        log.info("getting all the user who belong to the particular role District manager");
        return getAllUsers(page, size, sortBy, ascending, Role.DISTRICT_MANAGEMENT_STAFF);
    }

    public List<UserResponseDto> getAllCityManagers(int page, int size, String sortBy, boolean ascending) {
        log.info("getting all the user who belong to the particular role District manager");
        return getAllUsers(page, size, sortBy, ascending, Role.CITY_MANAGEMENT_STAFF);
    }

    public String updateProfile(UserRequestDto userRequestDto) {
        User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(()-> new CustomException(HttpStatus.BAD_REQUEST, "invalid request"));
        if(userRequestDto.firstName() != null) user.setFirstName(userRequestDto.firstName());
        if(userRequestDto.lastName() != null) user.setLastName(userRequestDto.lastName());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
        return "profile updated successfully";
    }




}
