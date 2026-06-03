package com.project.saas.service.global;

import com.project.saas.dto.global.request_dto.AssignStateRequestDto;
import com.project.saas.dto.global.responceDto.UserResponseDto;
import com.project.saas.entity.master.Cities;
import com.project.saas.entity.master.District;
import com.project.saas.entity.master.State;
import com.project.saas.entity.master.User;
import com.project.saas.exception.CustomException;
import com.project.saas.repo.global.CityRepo;
import com.project.saas.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CityService {

    private final CityRepo cityRepo;
    private final UserService userService;


    public String assignCityHead(AssignStateRequestDto assignStateRequestDto) {
        Cities cities = cityRepo.findCitiesByName(assignStateRequestDto.name()).orElseThrow(()-> new CustomException(HttpStatus.BAD_REQUEST,"the city not found with the name"));
        if(cities.getManagerUser()!=null) throw new CustomException(HttpStatus.BAD_REQUEST,"the city is already assigned");
        User user = userService.findById(assignStateRequestDto.managerId());

        cities.setManagerUser(user);
        cityRepo.save(cities);
        log.info("the city : {} has been assigned",assignStateRequestDto.name());
        return "success the city is assigned to :"+assignStateRequestDto.managerId();
    }

//    public List<UserResponseDto> getAllCityHeads() {
//        User manager = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(()-> new CustomException(HttpStatus.BAD_REQUEST, "invalid request"));
//        Cities cities = cityRepo.findCitiesByManagerUser_Id((manager.getId())).orElseThrow(()-> new CustomException(HttpStatus.BAD_REQUEST,"the city under you"));
//        List<District> districtList = cities.get().stream().filter(district -> district.getManagerUser()!=null).toList();
//        return districtList.stream().map(District::getManagerUser).map(user-> new UserResponseDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhone(), user.getCreatedAt(), user.getUpdatedAt())).toList();
//
//    }


//
//    public List<UserResponseDto> getAllDistrictHeads() {
//        }
}
