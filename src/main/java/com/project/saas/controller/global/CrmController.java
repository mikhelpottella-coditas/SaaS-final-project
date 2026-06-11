package com.project.saas.controller.global;

import com.project.saas.dto.global.request_dto.CustomerTenantRequestDto;
import com.project.saas.dto.global.request_dto.InvitationRequestDto;
import com.project.saas.dto.global.responceDto.TenantResponseDto;
import com.project.saas.service.InvitationService;
import com.project.saas.service.TenantService;
import com.project.saas.service.global.CustomerTenantService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/global/crm")
public class CrmController {

    private final TenantService tenantService;
    private final InvitationService invitationService;
    private final CustomerTenantService customerTenantService;

    @Operation(summary = "get all the tenants in a particular state")
    @GetMapping("state/{stateId}/tenants")
    public ResponseEntity<List<TenantResponseDto>> tenantByState(@PathVariable Long stateId,
                                                        @RequestParam(required = false,defaultValue = "0") int page,
                                                        @RequestParam(required = false,defaultValue = "5") int size,
                                                        @RequestParam(required = false,defaultValue = "id") String sortBy,
                                                        @RequestParam(required = false,defaultValue = "true") boolean ascending,
                                                        @RequestParam(required = false,defaultValue = "") String search) {
        return ResponseEntity.ok(tenantService.getByState(stateId,page,size,sortBy,ascending,search));
    }

    @Operation(summary = "send a invitation to the customer")
    @PostMapping("/invite/customer")
    public ResponseEntity<String> inviteCityManager(@Valid @RequestBody InvitationRequestDto invitationRequestDto){
        return ResponseEntity.ok(invitationService.inviteCustomer(invitationRequestDto));
    }


    @Operation(summary = "onboard a customer to a tenant")
    @PostMapping("/onboard/customer")
    public ResponseEntity<String> onBoardCustomer(@Valid @RequestBody CustomerTenantRequestDto customerTenantRequestDto){
        return  ResponseEntity.status(201).body(customerTenantService.onBoardCustomer(customerTenantRequestDto));

    }

}
