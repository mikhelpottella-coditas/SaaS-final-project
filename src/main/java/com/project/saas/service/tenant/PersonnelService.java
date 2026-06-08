package com.project.saas.service.tenant;

import com.project.saas.dto.tenant.response.ManagerResponseDto;
import com.project.saas.entity.tenant.TenantStateManager;
import com.project.saas.entity.tenant.TenantUser;
import com.project.saas.enums.Role;
import com.project.saas.exception.CustomException;
import com.project.saas.repo.tenant.TenantUserRepo;
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
public class PersonnelService {

    private final TenantUserRepo tenantUserRepo;
    private final TenantStateManagerService tenantStateManagerService;
    private final TenantUserCurdService tenantUserCurdService;


    public List<ManagerResponseDto> getAllPersonnel(int page, int size, String sortBy, boolean ascending, String search) {


        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        List<TenantUser> userList = tenantUserRepo.getByRole(Role.PERSONNEL, pageable);
        List<ManagerResponseDto> responseList = new ArrayList<>();
        userList.forEach((user) -> {
            List<Long> state = user.getTenantStateManagerPersonnel() == null ? null : user.getTenantStateManagerPersonnel().stream().map(m -> m.getId()).toList();
            ManagerResponseDto dto = new ManagerResponseDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhone(), user.getCreatedAt(), user.getUpdatedAt(), state, state != null);
            responseList.add(dto);
        });
        log.info("sending the list personnel staff");
        if (search.isEmpty()) return responseList;
        return responseList.stream().filter(u -> u.firstName().contains(search)).toList();


    }

    public ManagerResponseDto getPersonnelById(Long id) {
        TenantUser manager = tenantUserCurdService.getById(id);
        if (!manager.getRole().equals(Role.PERSONNEL))
            throw new CustomException(HttpStatus.BAD_REQUEST, "personnel not found with the given id");
        List<Long> state = manager.getTenantStateManagersM1() == null ? null : manager.getTenantStateManagersM1().stream().map(TenantStateManager::getId).toList();
        return new ManagerResponseDto(manager.getId(), manager.getFirstName(), manager.getLastName(), manager.getEmail(), manager.getPhone(), manager.getCreatedAt(), manager.getUpdatedAt(), state, state != null);

    }


    public String deleteById(Long id) {
        log.info("deleting the personnel");
        TenantUser manager = tenantUserCurdService.getById(id);
        if (!manager.getRole().equals(Role.PERSONNEL))
            throw new CustomException(HttpStatus.BAD_REQUEST, "personnel not found with the given id");

        List<TenantStateManager> tenantStatePersonnel = manager.getTenantStateManagerPersonnel();

        tenantStatePersonnel.forEach((tenantStateManager) -> {
            tenantStateManager.setPersonnel(null);
            tenantStateManagerService.save(tenantStateManager);
        });

        tenantUserRepo.delete(manager);

        log.info("the personnel delete successful with the id :{}", id);
        return "personnel delete successful with the id : " + id;
    }

}
