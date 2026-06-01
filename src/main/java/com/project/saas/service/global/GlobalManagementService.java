package com.project.saas.service.global;

import com.project.saas.dto.global.responceDto.UserResponseDto;
import com.project.saas.entity.master.User;
import com.project.saas.enums.Role;
import com.project.saas.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GlobalManagementService {

    private UserService userService;


    public List<UserResponseDto> getAllManagement(int page, int size, String sortBy, boolean ascending) {

        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        List<User> userList = userService.getByRole(Role.MANAGEMENT_STAFF,pageable);
        List<UserResponseDto> responseList = new ArrayList<>();
        userList.forEach((user) -> {
            UserResponseDto dto = new UserResponseDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhone(), user.getCreatedAt(), user.getUpdatedAt());
            responseList.add(dto);
        });
        log.info("sending the list management staff");
        return responseList;
    }
}
