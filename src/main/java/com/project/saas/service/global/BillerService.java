package com.project.saas.service.global;

import com.project.saas.dto.global.responceDto.BillerResponseDto;
import com.project.saas.entity.master.Area;
import com.project.saas.entity.master.Cities;
import com.project.saas.entity.master.User;
import com.project.saas.enums.Role;
import com.project.saas.exception.CustomException;
import com.project.saas.repo.global.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BillerService {

    private final AreaService areaService;
    private final UserCrudService userCrudService;
    private final UserRepository userRepository;
    private final UserService userService;
    private final CityService cityService;


    public String deleteById(Long billerId) {

        User biller = userCrudService.getById(billerId);

        Area area = biller.getBillerAreas();

        area.setBiller(null);
        areaService.save(area);

        userRepository.delete(biller);
        log.info("deleted the biller with the id :{}", billerId);
        return "deleted biller with id: " + billerId;
    }

    public String assignArea(Long billerId, Long areaId) {

        Area area = areaService.getById(areaId);
        if (area.getBiller() != null)
            throw new CustomException(HttpStatus.BAD_REQUEST, " the area already has a biller");
        User biller = userCrudService.getById(billerId);
        area.setBiller(biller);

        areaService.save(area);

        log.info("assigned the biller with the id :{} to the area : {}", billerId, area.getName());
        return "assigned the biller with the id: " + billerId;
    }

    public String reassignArea(Long billerId, Long areaId) {
        Area area = areaService.getById(areaId);
        User biller = userCrudService.getById(billerId);
        area.setBiller(biller);

        areaService.save(area);

        log.info("reassigned the biller with the id :{} to the area : {}", billerId, area.getName());
        return "reassigned the biller with the id: " + billerId;
    }

    public List<BillerResponseDto> getAll(int page, int size, String sortBy, boolean ascending, String search) {

        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        List<User> billerList = userService.getByRole(Role.BILLER, pageable);

        List<BillerResponseDto> billerResponseDtoList = new ArrayList<>();

        billerList.forEach(u->
        {
            Long areaId = u.getBillerAreas()==null?null:u.getBillerAreas().getId();
            billerResponseDtoList.add(new BillerResponseDto(u.getId(), u.getFirstName(), u.getLastName(), u.getEmail(), u.getPhone(), u.getCreatedAt(), u.getUpdatedAt(), areaId, areaId != null));
        });

        if(search.isEmpty()) return billerResponseDtoList;

        log.info("fetching all the biller details ");
        return billerResponseDtoList.stream().filter(b-> b.firstName().contains(search)).toList();

    }

    public BillerResponseDto getBillerById(Long id) {
        User biller =  userCrudService.getById(id);
        log.info("fetching the details of the biller with id :{}", id);
        Long areaId = biller.getBillerAreas()==null ? null : biller.getBillerAreas().getId();
        return new BillerResponseDto(biller.getId(), biller.getFirstName(), biller.getLastName(), biller.getEmail(), biller.getPhone(), biller.getCreatedAt(),biller.getUpdatedAt() , areaId,areaId!=null);
    }

    public List<BillerResponseDto> getByCity(Long cityId) {
        Cities cities = cityService.getById(cityId);
        if(cities.getAreaList()==null) throw  new CustomException(HttpStatus.BAD_REQUEST, "the areas are still not created in the city");
        log.info("fetching the biller of the particular city with id :{}", cityId);
        return cities.getAreaList().stream().filter(area -> area.getBiller() != null).map(Area::getBiller).map(biller -> new BillerResponseDto(biller.getId(), biller.getFirstName(), biller.getLastName(), biller.getEmail(), biller.getPhone(), biller.getCreatedAt(), biller.getUpdatedAt(), biller.getBillerAreas().getId(), true)).toList();
    }
}
