package com.project.saas.service.global;

import com.project.saas.dto.global.request_dto.UserRequestDto;
import com.project.saas.dto.global.responceDto.*;
import com.project.saas.entity.master.Cities;
import com.project.saas.entity.master.District;
import com.project.saas.entity.master.State;
import com.project.saas.entity.master.User;
import com.project.saas.enums.Role;
import com.project.saas.exception.CustomException;
import com.project.saas.repo.global.StateRepo;
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
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ManagerUserService {


    private final UserRepository userRepository;

    private final UserService userService;
    private final StateRepo stateRepo;

    public List<User> getAllUsers(int page, int size, String sortBy, boolean ascending,Role role) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        List<User> userList = userService.getByRole(role,pageable);
        log.info("getting all the user who belong to the particular role");
        return userList;
    }


    public List<StateManagerResponseDto> getAllStateManagers(int page, int size, String sortBy, boolean ascending,String search) {

        List<User> userList =  getAllUsers(page, size, sortBy, ascending, Role.STATE_MANAGEMENT_STAFF);

        List<StateManagerResponseDto> stateManagerResponseDtoList = new ArrayList<>();
        userList.forEach(u->{
            List<Long> stateIds = u.getManagerState()==null?null:u.getManagerState().stream().map(State::getId).toList();
            stateManagerResponseDtoList.add( new StateManagerResponseDto(u.getId(), u.getFirstName(), u.getLastName(), u.getEmail(), u.getPhone(), u.getCreatedAt(), u.getUpdatedAt(), stateIds,stateIds!=null));
        });
        log.info("getting all the user who belong to the particular role State manager");
        if(search.isEmpty()) return stateManagerResponseDtoList;
        return stateManagerResponseDtoList.stream().filter(s->s.firstName().contains(search)).toList();
    }





    public List<DistrictMangerResponseDto> getAllDistrictManagers(int page, int size, String sortBy, boolean ascending,String search) {
        log.info("getting all the user who belong to the particular role District manager");
        List<User> userList = getAllUsers(page, size, sortBy, ascending, Role.DISTRICT_MANAGEMENT_STAFF);

        List<DistrictMangerResponseDto> districtManagerResponseDtoList = new ArrayList<>();
        userList.forEach(u->{
            List<Long> districtIds = u.getManagerDistrict()==null?null:u.getManagerDistrict().stream().map(District::getId).toList();
            districtManagerResponseDtoList.add( new DistrictMangerResponseDto(u.getId(), u.getFirstName(), u.getLastName(), u.getEmail(), u.getPhone(), u.getCreatedAt(), u.getUpdatedAt(), districtIds,districtIds!=null));
        });
        log.info("return the data of the district heads");
        if (search.isEmpty())return districtManagerResponseDtoList;
        return districtManagerResponseDtoList.stream().filter(s->s.firstName().contains(search)).toList();
     }








    public List<CityMangerResponseDto> getAllCityManagers(int page, int size, String sortBy, boolean ascending,String search) {
        log.info("getting all the user who belong to the particular role District manager");
        List<User> userList =  getAllUsers(page, size, sortBy, ascending, Role.CITY_MANAGEMENT_STAFF);

        List<CityMangerResponseDto> citymanagerResponseDtoList = new ArrayList<>();
        userList.forEach(u->{
            List<Long> cityIds = u.getManagerCities()==null?null:u.getManagerCities().stream().map(Cities::getId).toList();
            citymanagerResponseDtoList.add( new CityMangerResponseDto(u.getId(), u.getFirstName(), u.getLastName(), u.getEmail(), u.getPhone(), u.getCreatedAt(), u.getUpdatedAt(), cityIds,cityIds!=null));
        });
        log.info("retuning the data of the city head of all");
        if(search.isEmpty()) return citymanagerResponseDtoList;
        return citymanagerResponseDtoList.stream().filter(c->c.firstName().contains(search)).toList();
    }


    public List<StateResponseDto> getAllStates(int page, int size, String sortBy, boolean ascending, String search) {

        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        List<State> stateList = stateRepo.findAll(pageable).getContent();

        List<StateResponseDto> stateResponseDtoList = new ArrayList<>();

        stateList.forEach(state->{
            Long managerId = state.getManagerUser()==null?null:state.getManagerUser().getId();
            List<Long> districtIds = state.getDistrictList()==null?null:state.getDistrictList().stream().map(District::getId).toList();
            stateResponseDtoList.add( new StateResponseDto(state.getId(), state.getName(), managerId, districtIds));
        });

        if(search.isEmpty())return stateResponseDtoList;

        return stateResponseDtoList.stream().filter(s->s.name().contains(search)).toList();

    }
}
