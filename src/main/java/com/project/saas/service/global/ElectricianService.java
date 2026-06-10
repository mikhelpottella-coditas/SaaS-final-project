package com.project.saas.service.global;

import com.project.saas.dto.global.responceDto.ElectricianResponseDto;
import com.project.saas.entity.master.Area;
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
public class ElectricianService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final UserCrudService userCrudService;
    private final AreaService areaService;


    public String deleteById(Long electricianId) {

        User electrician = userCrudService.getById(electricianId);

        Area area = electrician.getElectrianAreas();

        area.setElectrician(null);
        areaService.save(area);

        userRepository.delete(electrician);
        log.info("deleted the electrician with the id :{}", electricianId);
        return "deleted electrician with id: " + electricianId;
    }

    public String assignArea(Long electricianId, Long areaId) {

        Area area = areaService.getById(areaId);
        if (area.getElectrician() != null)
            throw new CustomException(HttpStatus.BAD_REQUEST, " the area already has a electrician");
        User electrician = userCrudService.getById(electricianId);
        area.setElectrician(electrician);

        areaService.save(area);

        log.info("assigned the electrician with the id :{} to the area : {}", electricianId, area.getName());
        return "assigned the electrician with the id: " + electricianId;
    }


    public String reassignArea(Long electricianId, Long areaId) {
        Area area = areaService.getById(areaId);
        User electrician = userCrudService.getById(electricianId);
        area.setElectrician(electrician);

        areaService.save(area);

        log.info("reassigned the electrician with the id :{} to the area : {}", electricianId, area.getName());
        return "reassigned the electrician with the id: " + electricianId;
    }

    public List<ElectricianResponseDto> getAll(int page, int size, String sortBy, boolean ascending, String search) {

        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        List<User> electricianList = userService.getByRole(Role.ELECTRICIAN, pageable);



        List<ElectricianResponseDto> electricianResponseDtoList = new ArrayList<>();

        electricianList.forEach(u->{
            Long areaId = u.getBillerAreas() == null ? null : u.getBillerAreas().getId();
            electricianResponseDtoList.add(new ElectricianResponseDto(
                    u.getId(), u.getFirstName(),
                    u.getLastName(), u.getEmail(),
                    u.getPhone(), u.getCreatedAt(),
                    u.getUpdatedAt(), areaId,areaId!=null));
        });

        if(search.isEmpty()) return electricianResponseDtoList;

        log.info("fetching all the electrician details ");
        return electricianResponseDtoList.stream().filter(b-> b.firstName().contains(search)).toList();


    }

    public ElectricianResponseDto getElectricianById(Long id) {
        User electrician =  userCrudService.getById(id);
        log.info("fetching the details of the electrician with id :{}", id);
        Long areaId = electrician.getBillerAreas()==null ? null : electrician.getBillerAreas().getId();
        return new ElectricianResponseDto(electrician.getId(), electrician.getFirstName(), electrician.getLastName(), electrician.getEmail(), electrician.getPhone(), electrician.getCreatedAt(),electrician.getUpdatedAt() , areaId,areaId!=null);
    }

    public List<ElectricianResponseDto> getAllByArea(Long Id, int page, int size, String sortBy, boolean ascending, String search) {
        log.info("start fetching the details of the electrics by area");
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Area area = areaService.getById(Id);

        List<User> electricianList = userService.getByRole(Role.ELECTRICIAN, pageable);



        List<ElectricianResponseDto> electricianResponseDtoList = new ArrayList<>();

        electricianList.stream().filter(e->e.getElectrianAreas().equals(area)).forEach(u->{
            Long areaId = u.getBillerAreas() == null ? null : u.getBillerAreas().getId();
            electricianResponseDtoList.add(new ElectricianResponseDto(
                    u.getId(), u.getFirstName(),
                    u.getLastName(), u.getEmail(),
                    u.getPhone(), u.getCreatedAt(),
                    u.getUpdatedAt(), areaId,areaId!=null));
        });

        if(search.isEmpty()) return electricianResponseDtoList;

        log.info("fetching all the electrician details ");
        return electricianResponseDtoList.stream().filter(b-> b.firstName().contains(search)).toList();




    }
}
