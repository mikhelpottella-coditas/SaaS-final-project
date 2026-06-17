package com.project.saas.service.global;

import com.project.saas.dto.global.request_dto.ServiceAreaRequestDto;
import com.project.saas.dto.global.responceDto.AreaResponseDto;
import com.project.saas.entity.master.Area;
import com.project.saas.entity.master.Cities;
import com.project.saas.entity.master.User;
import com.project.saas.enums.Role;
import com.project.saas.exception.CustomException;
import com.project.saas.repo.global.AreaRepo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AreaService {

    private final AreaRepo areaRepo;
    private final CityService cityService;


    public String createArea(@Valid ServiceAreaRequestDto areaRequestDto) {
        Cities city = cityService.getById(areaRequestDto.cityId());
        if (!city.getAreaList().stream().map(Area::getName).filter(areaName -> areaName.equals(areaRequestDto.areaName())).toList().isEmpty())
            throw new CustomException(HttpStatus.BAD_REQUEST, "the area in that city is already exists");

        Area area = Area.builder().name(areaRequestDto.areaName()).code(areaRequestDto.code()).city(city).build();

        areaRepo.save(area);

        log.info("the area is created with the code : {}", area.getCode());
        return "area created successfully with name : " + areaRequestDto.areaName();
    }

    public String deleteArea(Long areaId) {
        Area area = areaRepo.findById(areaId).orElseThrow(()-> new CustomException(HttpStatus.NOT_FOUND,"the area you are trying to access is not available"));
        areaRepo.delete(area);
        log.info("the area is deleted with the code : {}", area.getCode());
        return "area has been deleted successfully";
    }

    public String updateArea(Long areaId, ServiceAreaRequestDto areaRequestDto) {
        Area area = areaRepo.findById(areaId).orElseThrow(()-> new CustomException(HttpStatus.NOT_FOUND,"the area you are trying to access is not available"));

        if(!areaRequestDto.areaName().isEmpty()) area.setName(areaRequestDto.areaName());
        if(!areaRequestDto.code().isEmpty()) area.setCode(areaRequestDto.code());
        if(areaRequestDto.cityId()!=null){
            Cities city = cityService.getById(areaRequestDto.cityId());
            area.setCity(city);
        }

        areaRepo.save(area);

        log.info("the area is updated with the code : {}", area.getCode());
        return "the area is successfully updated with the given data";
    }

    public void save(Area area) {
        areaRepo.save(area);
    }

    public Area getById(Long areaId) {
        return areaRepo.findById(areaId).orElseThrow(()-> new CustomException(HttpStatus.NOT_FOUND, " the are is not available"));
    }

    public List<AreaResponseDto> getAll(Long cityId, int page, int size, String sortBy, boolean ascending, String search) {

        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        List<Area> areaList = areaRepo.findAllByCity_Id(cityId, pageable).orElseThrow(()-> new CustomException(HttpStatus.NOT_FOUND,"there are no areas under this city"));

        List<AreaResponseDto> areaResponseDtoList = new ArrayList<>();

        areaList.forEach(area->{
            areaResponseDtoList.add(new AreaResponseDto(area.getId(),area.getName()));
        });

        if(search.isEmpty()) return areaResponseDtoList;
        return areaResponseDtoList.stream().filter(a->a.name().contains(search)).toList();

    }

    public AreaResponseDto getAreaById(Long areaId) {
        Area area = getById(areaId);
        return new AreaResponseDto(areaId,area.getName());
    }
}