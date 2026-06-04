package com.project.saas.service.global;

import com.project.saas.dto.global.request_dto.AssignStateRequestDto;
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
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CityService {

    private final CityRepo cityRepo;
    private final UserService userService;
    private final DistrictRepo districtRepo;
    private final UserRepository userRepository;


    public String assignCityHead(AssignStateRequestDto assignStateRequestDto) {
        Cities cities = cityRepo.findCitiesByName(assignStateRequestDto.name()).orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, "the city not found with the name"));
        if (cities.getManagerUser() != null)
            throw new CustomException(HttpStatus.BAD_REQUEST, "the city is already assigned");
        User user = userService.findById(assignStateRequestDto.managerId());

        cities.setManagerUser(user);
        cityRepo.save(cities);
        log.info("the city : {} has been assigned", assignStateRequestDto.name());
        return "success the city is assigned to :" + assignStateRequestDto.managerId();
    }

    public List<CityMangerResponseDto> getCityHeadsUnassigned(int page, int size, String sortBy, boolean ascending, String search) {

        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        List<User> cityHeadList = userRepository.findAllByRoleAndManagerCitiesIsEmpty(Role.CITY_MANAGEMENT_STAFF, pageable).getContent();
        log.info("fetching the users who are district heads and that are not assigned to any district");
        List<CityMangerResponseDto> finalList = cityHeadList.stream().map(u -> new CityMangerResponseDto(u.getId(), u.getFirstName(), u.getLastName(), u.getEmail(), u.getPhone(), u.getCreatedAt(), u.getUpdatedAt(), null)).toList();

        if (search.isEmpty()) return finalList;
        log.info("using the search to find the match");

        return finalList.stream().filter(u -> u.firstName().contains(search)).toList();
    }

//    public List<UserResponseDto> getAllCityHeads(int page, int size, String sortBy, boolean ascending, String search) {
//
//        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
//        Pageable pageable = PageRequest.of(page, size, sort);
//
//        List<User> cityHeadList = userRepository.findAllByRole(Role.CITY_MANAGEMENT_STAFF,pageable).getContent();
//
//        List<UserResponseDto> finalList = cityHeadList.stream().map(u-> new UserResponseDto(u.getId(), u.getFirstName(), u.getLastName(), u.getEmail(), u.getPhone(), u.getCreatedAt(), u.getUpdatedAt())).toList();
//        if(search.isEmpty()) return finalList;
//
//        return finalList.stream().filter(u->u.firstName().contains(search)).toList();
//
//    }

    public Cities getById(Long id) {
        return cityRepo.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "the city that you are trying to access is not found"));
    }

    public String reassignCityHead(AssignStateRequestDto assignStateRequestDto) {
        Cities cities = cityRepo.findCitiesByName(assignStateRequestDto.name()).orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, "the city not found with the name"));
        User user = userService.findById(assignStateRequestDto.managerId());

        cities.setManagerUser(user);
        cityRepo.save(cities);
        log.info("the city : {} has been assigned", assignStateRequestDto.name());
        return "success the city is assigned to :" + assignStateRequestDto.managerId();
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
