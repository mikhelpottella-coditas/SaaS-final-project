package com.project.saas.service.global;

import com.project.saas.dto.global.request_dto.UserRequestDto;
import com.project.saas.dto.global.responceDto.CityMangerResponseDto;
import com.project.saas.dto.global.responceDto.DistrictMangerResponseDto;
import com.project.saas.dto.global.responceDto.StateManagerResponseDto;
import com.project.saas.dto.global.responceDto.UserResponseDto;
import com.project.saas.entity.master.Cities;
import com.project.saas.entity.master.District;
import com.project.saas.entity.master.State;
import com.project.saas.entity.master.User;
import com.project.saas.enums.Role;
import com.project.saas.exception.CustomException;
import com.project.saas.repo.global.UserRepository;
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

    public List<User> getAllUsers(int page, int size, String sortBy, boolean ascending,Role role) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        List<User> userList = userService.getByRole(role,pageable);
        log.info("getting all the user who belong to the particular role");
        return userList;
    }


    public List<StateManagerResponseDto> getAllStateManagers(int page, int size, String sortBy, boolean ascending) {

        List<User> userList =  getAllUsers(page, size, sortBy, ascending, Role.STATE_MANAGEMENT_STAFF);

        List<StateManagerResponseDto> stateManagerResponseDtoList = userList.stream().map(u->new StateManagerResponseDto(u.getId(), u.getFirstName(), u.getLastName(), u.getEmail(), u.getPhone(), u.getCreatedAt(), u.getUpdatedAt(), u.getManagerState().stream().map(State::getName).toList())).toList();
        log.info("getting all the user who belong to the particular role State manager");
        return stateManagerResponseDtoList;
    }

    public List<DistrictMangerResponseDto> getAllDistrictManagers(int page, int size, String sortBy, boolean ascending) {
        log.info("getting all the user who belong to the particular role District manager");
        List<User> userList = getAllUsers(page, size, sortBy, ascending, Role.DISTRICT_MANAGEMENT_STAFF);

        List<DistrictMangerResponseDto> districtMangerResponseDtoList = userList.stream().map(u -> new DistrictMangerResponseDto(u.getId(), u.getFirstName(), u.getLastName(), u.getEmail(), u.getPhone(), u.getCreatedAt(), u.getUpdatedAt(), u.getManagerDistrict().stream().map(District::getName).toList())).toList();
        log.info("return the data of the district heads");
        return districtMangerResponseDtoList;
     }

    public List<CityMangerResponseDto> getAllCityManagers(int page, int size, String sortBy, boolean ascending,String search) {
        log.info("getting all the user who belong to the particular role District manager");
        List<User> userList =  getAllUsers(page, size, sortBy, ascending, Role.CITY_MANAGEMENT_STAFF);

        List<CityMangerResponseDto> cityMangerResponseDtoList = userList.stream().map(u -> new CityMangerResponseDto(u.getId(), u.getFirstName(), u.getLastName(), u.getEmail(), u.getPhone(), u.getCreatedAt(), u.getUpdatedAt(), u.getManagerCities().stream().map(Cities::getName).toList())).toList();
        log.info("retuning the data of the city head of all");
        if(search.isEmpty()) return cityMangerResponseDtoList;
        return cityMangerResponseDtoList.stream().filter(c->c.firstName().contains(search)).toList();
    }




}
