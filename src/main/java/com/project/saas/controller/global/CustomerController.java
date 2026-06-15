package com.project.saas.controller.global;

import com.project.saas.dto.global.request_dto.AddAddressRequestDto;
import com.project.saas.dto.global.responceDto.CustomerResponseDto;
import com.project.saas.dto.global.responceDto.TenantResponseDto;
import com.project.saas.service.TenantService;
import com.project.saas.service.global.CustomerService;
import com.project.saas.service.global.CustomerTenantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.models.annotations.OpenAPI30;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/global/customer")
public class CustomerController {
    private final TenantService tenantService;
    private final CustomerTenantService customerTenantService;
    private final CustomerService customerService;

    // get all tenants

    @PostMapping("/{userId}/add-address")
    public ResponseEntity<String> addAddress(@PathVariable Long userId,@RequestBody AddAddressRequestDto addAddressRequestDto){
        return ResponseEntity.ok(customerService.addAddress(userId,addAddressRequestDto));
    }

    @Operation(summary = "get all the tenants to a customer")
    @GetMapping("{customerId}/tenants")
    public ResponseEntity<List<TenantResponseDto>> getCustomerTenants(@PathVariable Long customerId){
        return ResponseEntity.ok(customerTenantService.getByCustomer(customerId));
    }



}
