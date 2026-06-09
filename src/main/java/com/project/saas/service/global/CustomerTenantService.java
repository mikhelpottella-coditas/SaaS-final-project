package com.project.saas.service.global;

import com.project.saas.dto.global.request_dto.CustomerTenantRequestDto;
import com.project.saas.dto.global.responceDto.TenantResponseDto;
import com.project.saas.entity.master.Area;
import com.project.saas.entity.master.Customer;
import com.project.saas.entity.master.CustomerTenant;
import com.project.saas.entity.master.Tenant;
import com.project.saas.exception.CustomException;
import com.project.saas.repo.global.CustomerTenantRepo;
import com.project.saas.service.TenantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerTenantService {

    private final CustomerTenantRepo  customerTenantRepo;
    private final TenantService tenantService;
    private final CustomerService customerService;
    private final AreaService areaService;


    public String onBoardCustomer(@Valid CustomerTenantRequestDto customerTenantRequestDto) {

        log.info("onboarding new customer to a particular tenant id : {}",customerTenantRequestDto.tenantId());
        Tenant tenant = tenantService.getById(customerTenantRequestDto.tenantId());
        Customer customer = customerService.findById(customerTenantRequestDto.customerId());
        Area area = areaService.getById(customerTenantRequestDto.areaId());

        CustomerTenant customerTenant = CustomerTenant.builder()
                .customer(customer)
                .area(area)
                .tenant(tenant)
                .isActive(true)
                .build();

        customerTenantRepo.save(customerTenant);

        log.info("onboarding the customer is done");
        return "customer onboarding is done";
    }

    public List<TenantResponseDto> getByCustomer(Long customerId) {
        List<CustomerTenant> customerTenantList = customerTenantRepo.findAllByCustomer_Id(customerId);
        if(customerTenantList==null) throw new CustomException(HttpStatus.NOT_FOUND, "customer tenants not found");

        List<Tenant> tenantList = customerTenantList.stream().map(CustomerTenant::getTenant).toList();

        log.info("getting all the tenants of the particular customer whose id : {}",customerId);
        return tenantList.stream().map(t->new TenantResponseDto(t.getId(), t.getName(), t.getSchemaName(), t.getTenantStatus(), t.getCreatedAt(),t.getUpdatedAt(), null, null)).toList();

    }
}
