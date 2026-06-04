package com.project.saas.service.global;

import com.project.saas.dto.global.request_dto.DistrictRequestDto;
import com.project.saas.dto.global.responceDto.CityMangerResponseDto;
import com.project.saas.dto.global.responceDto.UserResponseDto;
import com.project.saas.entity.master.Cities;
import com.project.saas.entity.master.District;
import com.project.saas.entity.master.State;
import com.project.saas.entity.master.User;
import com.project.saas.enums.Role;
import com.project.saas.exception.CustomException;
import com.project.saas.repo.global.CityRepo;
import com.project.saas.repo.global.DistrictRepo;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class DistrictService {

    private final DistrictRepo districtRepo;
    private final UserService userService;
    private final StateService stateService;
    private final CityRepo cityRepo;
    private final UserRepository userRepository;
    private final ManagerUserService managerUserService;


    public String createDistrict(DistrictRequestDto districtRequestDto) {
        User manager = userService.findByUsername(Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName()).orElseThrow(()-> new CustomException(HttpStatus.BAD_REQUEST, "invalid request"));
        log.info("trying the fetch the state of the user");
        State state = manager.getManagerState().stream().filter(s->s.getId().equals(districtRequestDto.stateId())).findFirst().orElseThrow(()-> new CustomException(HttpStatus.NOT_FOUND, "the state does not exist under your authority "));

        log.info("if the district wih the same name already there then throw the error");
        if(!districtRepo.existsDistrictByName(districtRequestDto.districtName())) throw new CustomException(HttpStatus.BAD_REQUEST, "the district is already exists with that name");

        District district = District.builder()
                .state(state)
                .name(districtRequestDto.districtName())
                .code(districtRequestDto.code())
                .build();

        districtRepo.save(district);

        return "new district is created added successfully in state :"+state.getName();
    }


    public List<UserResponseDto> getAllDistrictHeads(int page, int size, String sortBy, boolean ascending, String search) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        log.info("checking the user is valid or not");
        User manager = userService.findByUsername(Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName()).orElseThrow(()-> new CustomException(HttpStatus.BAD_REQUEST, "invalid request"));

        List<State> states = manager.getManagerState();
        if(states == null) throw new CustomException(HttpStatus.BAD_REQUEST, "states not found");
        log.info("trying to get all districts of all the states that we have");
        log.warn("page is getting X states times have to recheck this thing");
        List<District> districtList = new ArrayList<>();
        states.forEach(s-> districtList.addAll(districtRepo.findAllByState(s,pageable).stream().filter(district -> district.getManagerUser()!=null).toList()));
        List<UserResponseDto> finalList = districtList.stream().map(District::getManagerUser).map(user-> new UserResponseDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhone(), user.getCreatedAt(), user.getUpdatedAt())).toList();

        if(search.isBlank()) return finalList;
        return finalList.stream().filter(dto-> dto.firstName().contains(search)).toList();
    }

    public UserResponseDto getDistrictHeadById(Long id) {
        log.info("first fetching the all the district head then we can find the one we wanted");
        List<UserResponseDto> userResponseDtoList = getAllDistrictHeads(0,5,"id",true,"");


        return userResponseDtoList.stream().filter(userResponseDto -> userResponseDto.id().equals(id)).findFirst().orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, "invalid request, the district head not found with the id"));


    }


    public String assignDistrictHead(Long districtId, Long headId, Long stateId) {
        User manager = userService.findByUsername(Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName()).orElseThrow(()-> new CustomException(HttpStatus.BAD_REQUEST, "invalid request"));
        manager.getManagerState().stream().filter(s->s.getId().equals(stateId)).findFirst().orElseThrow(()-> new CustomException(HttpStatus.BAD_REQUEST, "the state is under you authority"));
        District district = districtRepo.findById(districtId).orElseThrow(()-> new CustomException(HttpStatus.BAD_REQUEST, "the district is not available"));
        User districtHead = userService.findById(headId);
        district.setManagerUser(districtHead);
        districtRepo.save(district);
        log.info("the district head : {} is assigned to the district : {}", districtHead.getFirstName(), district.getName());
        return "assigned district head";
    }


    public List<CityMangerResponseDto> getAllCityHeadsAssigned(int page, int size, String sortBy, boolean ascending, String search) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        List<User> cityHead = userRepository.findAllByRoleAndManagerCitiesIsNotEmpty(Role.CITY_MANAGEMENT_STAFF,pageable).getContent();
        log.info("fetching all the city head who are assigned to the cities");
        List<CityMangerResponseDto> cityMangerResponseDtoList = cityHead.stream().map(u -> new CityMangerResponseDto(u.getId(), u.getFirstName(), u.getLastName(), u.getEmail(), u.getPhone(), u.getCreatedAt(), u.getUpdatedAt(), u.getManagerCities().stream().map(Cities::getId).toList())).toList();
        if(search.isBlank()) return cityMangerResponseDtoList;
        return cityMangerResponseDtoList.stream().filter(c->c.firstName().contains(search)).toList();
    }



    public CityMangerResponseDto getCityHeadById(Long headId) {
        List<CityMangerResponseDto> cityMangerResponseDtoList = managerUserService.getAllCityManagers(0, 5, "id", true, "");
        return cityMangerResponseDtoList.stream().filter(userResponseDto -> userResponseDto.id().equals(headId)).findFirst().orElseThrow(()-> new CustomException(HttpStatus.BAD_REQUEST, "invalid request, the district head not found with the id"));
    }


}
