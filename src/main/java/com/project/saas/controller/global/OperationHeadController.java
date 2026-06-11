package com.project.saas.controller.global;


import com.project.saas.dto.global.request_dto.TenantRequestDto;
import com.project.saas.dto.global.responceDto.SubscriptionBillsResponseDto;
import com.project.saas.service.TenantService;
import com.project.saas.service.global.TenantSubscriptionBillService;
import com.project.saas.service.global.UserCrudService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/global/operational-head")
public class OperationHeadController {

    private final TenantService tenantService;
    private final UserCrudService userCrudService;
    private final TenantSubscriptionBillService tenantSubscriptionBillService;


    @PostMapping("/tenant/register")
    @Operation(
            summary = "register a provider by operational head",
            description = "the operational head provide the details of the provider in the request body and registers a new operational head"
    )
    public ResponseEntity<String> tenantRegister(@Valid @RequestBody TenantRequestDto tenantRequestDto){
        return ResponseEntity.ok(tenantService.tenantRegistration(tenantRequestDto));
    }



    @Operation(
            summary = "updating the tenant details",
            description = "the operation head can change or update the details of the tenant like the subscription details"
    )
    @PutMapping("/tenant/{id}")
    public ResponseEntity<String> tenantUpdate( @RequestBody TenantRequestDto tenantRequestDto,@PathVariable Long id){
        return ResponseEntity.ok(tenantService.update(tenantRequestDto,id));
    }


    @Operation(summary = "get all the bills of the tenant by the tenant id")
    @GetMapping("/tenant/{id}/get-bills")
    public ResponseEntity<List<SubscriptionBillsResponseDto>> getBills(@PathVariable Long id){
        return ResponseEntity.ok(tenantSubscriptionBillService.getBills(id));
    }



    @Operation(summary = "pay the bill of a tenant")
    @PatchMapping("/tenant/pay-bills/{billId}")
    public ResponseEntity<String> payBills(@PathVariable Long billId){
        return ResponseEntity.ok(tenantSubscriptionBillService.payBill(billId));
    }

}
