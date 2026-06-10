package com.project.saas.controller.global;

import com.project.saas.dto.global.responceDto.TenantResponseDto;
import com.project.saas.service.TenantService;
import com.project.saas.service.global.CustomerTenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/global/customer")
public class CustomerController {
    private final TenantService tenantService;
    private final CustomerTenantService customerTenantService;

    // get all tenants

    @GetMapping("{customerId}/tenants")
    public ResponseEntity<List<TenantResponseDto>> getCustomerTenants(@PathVariable Long customerId){
        return ResponseEntity.ok(customerTenantService.getByCustomer(customerId));
    }



}
