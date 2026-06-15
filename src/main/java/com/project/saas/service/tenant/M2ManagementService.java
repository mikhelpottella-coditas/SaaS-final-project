
package com.project.saas.service.tenant;

import com.project.saas.dto.tenant.response.ManagerResponseDto;
import com.project.saas.entity.tenant.TenantStateManager;
import com.project.saas.entity.tenant.TenantUser;
import com.project.saas.enums.Role;
import com.project.saas.exception.CustomException;
import com.project.saas.repo.tenant.TenantUserRepo;
import jakarta.transaction.Transactional;
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
@Transactional
@RequiredArgsConstructor
@Slf4j
public class M2ManagementService {

    private final TenantUserRepo tenantUserRepo;
    private final TenantStateManagerService tenantStateManagerService;
    private final TenantUserCurdService tenantUserCurdService;
    private final TenantRefreshTokenService tenantRefreshTokenService;


    public List<ManagerResponseDto> getAllM2Management(int page, int size, String sortBy, boolean ascending, String search) {

        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        List<TenantUser> userList = tenantUserRepo.getByRole(Role.M2_MANAGER, pageable);
        List<ManagerResponseDto> responseList = new ArrayList<>();
        userList.forEach(user -> {
            List<Long> state = user.getTenantStateManagersM1() == null ? null : user.getTenantStateManagersM1().stream().map(m -> m.getId()).toList();
            ManagerResponseDto dto = new ManagerResponseDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhone(), user.getCreatedAt(), user.getUpdatedAt(), state, state != null);
            responseList.add(dto);
        });
        log.info("sending the list management staff");
        if (search.isEmpty()) return responseList;
        return responseList.stream().filter(u -> u.firstName().contains(search)).toList();

    }

    public ManagerResponseDto getM2ManagementById(Long id) {
        TenantUser manager = tenantUserCurdService.getById(id);
        if (!manager.getRole().equals(Role.M2_MANAGER))
            throw new CustomException(HttpStatus.BAD_REQUEST, "management staff not found with the given id");
        List<Long> state = manager.getTenantStateManagersM1() == null ? null : manager.getTenantStateManagersM1().stream().map(m -> m.getId()).toList();
        return new ManagerResponseDto(manager.getId(), manager.getFirstName(), manager.getLastName(), manager.getEmail(), manager.getPhone(), manager.getCreatedAt(), manager.getUpdatedAt(), state, state != null);

    }

    public String deleteById(Long id) {
        log.info("deleting the management staff");
        TenantUser manager = tenantUserCurdService.getById(id);
        if (!manager.getRole().equals(Role.M2_MANAGER))
            throw new CustomException(HttpStatus.BAD_REQUEST, "management staff not found with the given id");

        List<TenantStateManager> tenantStateManagersM2 = manager.getTenantStateManagersM2();

        tenantStateManagersM2.forEach(tenantStateManager -> {
            tenantStateManager.setM2Manager(null);
            tenantStateManagerService.save(tenantStateManager);
        });

        tenantRefreshTokenService.deleteToken(manager);

        tenantUserRepo.delete(manager);

        log.info("the management staff delete successful with the id :{}", id);
        return "management staff delete successful with the id : " + id;
    }
}

