package com.project.saas.service.global;

import com.project.saas.dto.global.responceDto.BillerResponseDto;
import com.project.saas.dto.global.responceDto.CrmResponseDto;
import com.project.saas.dto.global.responceDto.ElectricianResponseDto;
import com.project.saas.entity.master.Area;
import com.project.saas.entity.master.Cities;
import com.project.saas.entity.master.Crm;
import com.project.saas.entity.master.User;
import com.project.saas.enums.Role;
import com.project.saas.exception.CustomException;
import com.project.saas.repo.global.CrmRepo;
import com.project.saas.repo.global.CustomerRepo;
import com.project.saas.repo.global.UserRepository;
import com.project.saas.service.UserService;
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
public class CrmService {

    private final AreaService areaService;
    private final UserService userService;
    private final UserCrudService userCrudService;
    private final UserRepository userRepository;
    private final CrmRepo crmRepo;
    private final CustomerRepo customerRepo;
    private final CustomerService customerService;
    private final CityService cityService;


    public String deleteById(Long crmId) {
        Crm crm = crmRepo.findById(crmId).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, " the crm that you are accessing is not found "));

        crm.getCustomer().forEach(c -> {
            c.setCrm(null);
            customerService.save(c);
        });
        crmRepo.delete(crm);

        log.info("the crm is deleted successfully with the id : {}", crmId);
        return "the crm is deleted successfully with the id : " + crmId;
    }


    public String assignCity(Long crmId, Long cityId) {
        Cities cities = cityService.getById(cityId);

        User crmUser = userService.findById(crmId);

        if (crmRepo.existsCrmByUser(crmUser))
            throw new CustomException(HttpStatus.BAD_REQUEST, " the crm is already assigned to other area");
        Crm crm = Crm.builder().cities(cities).user(crmUser).build();
        crmRepo.save(crm);

        log.info("the crm is assigned to the city successfully with the id : {}", crmId);
        return "the crm is assigned to the city successfully with the id : " + crmId;
    }


    public String reassignCity(Long crmId, Long cityId) {
        Cities cities = cityService.getById(cityId);

        User crmUser = userService.findById(crmId);

        Crm crm = Crm.builder().cities(cities).user(crmUser).build();

        crmRepo.save(crm);

        log.info("the crm is assigned to the city successfully with the id : {}", crmId);
        return "the crm is assigned to the city successfully with the id : " + crmId;

    }

    public List<CrmResponseDto> getAll(int page, int size, String sortBy, boolean ascending, String search) {


        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        List<User> crmList = userService.getByRole(Role.CMR, pageable);

        List<CrmResponseDto> crmResponseDtoList = new ArrayList<>();

        crmList.forEach(u -> {
            Long areaId = u.getBillerAreas() == null ? null : u.getBillerAreas().getId();
            crmResponseDtoList.add(new CrmResponseDto(u.getId(), u.getFirstName(), u.getLastName(), u.getEmail(), u.getPhone(), u.getCreatedAt(), u.getUpdatedAt(), areaId, areaId != null));
        });

        if (search.isEmpty()) return crmResponseDtoList;

        log.info("fetching all the crm details ");
        return crmResponseDtoList.stream().filter(b -> b.firstName().contains(search)).toList();
    }


    public CrmResponseDto getCrmById(Long id) {
        User crmUser = userService.findById(id);

        Long cityId  = crmUser.getCrm()==null?null:crmUser.getCrm().getCities().getId();

        log.info("getting the crm details ");
        return new CrmResponseDto(crmUser.getCrm().getId(), crmUser.getFirstName(), crmUser.getLastName(), crmUser.getEmail(), crmUser.getPhone(), crmUser.getCreatedAt(), crmUser.getUpdatedAt(), cityId, cityId != null);
    }
}
