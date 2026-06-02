package com.project.saas.service.global;

import com.project.saas.dto.global.responceDto.UserResponseDto;
import com.project.saas.entity.master.District;
import com.project.saas.entity.master.State;
import com.project.saas.entity.master.User;
import com.project.saas.exception.CustomException;
import com.project.saas.repo.global.DistrictRepo;
import com.project.saas.repo.global.StateRepo;
import com.project.saas.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DistrictService {

    private final DistrictRepo districtRepo;
    private final UserService userService;
    private final StateService stateService;
    private final StateRepo stateRepo;

    public List<UserResponseDto> getAllDistrictHeads() {
        User manager = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(()-> new CustomException(HttpStatus.BAD_REQUEST, "invalid request"));
        State state = stateService.getByStateHead(manager.getId());
        List<District> districtList = state.getDistrictList().stream().filter(district -> district.getManagerUser()!=null).toList();
        return districtList.stream().map(District::getManagerUser).map(user-> new UserResponseDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhone(), user.getCreatedAt(), user.getUpdatedAt())).toList();
    }
}
