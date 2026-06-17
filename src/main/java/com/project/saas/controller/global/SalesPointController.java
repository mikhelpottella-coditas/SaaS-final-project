package com.project.saas.controller.global;

import com.project.saas.dto.global.request_dto.InvitationRequestDto;
import com.project.saas.dto.global.responceDto.SubscriptionBillsResponseDto;
import com.project.saas.dto.global.responceDto.TenantResponseDto;
import com.project.saas.dto.global.responceDto.UserResponseDto;
import com.project.saas.enums.TenantStatus;
import com.project.saas.service.InvitationService;
import com.project.saas.service.TenantService;
import com.project.saas.service.global.AdminService;
import com.project.saas.service.global.TenantSubscriptionBillService;
import com.project.saas.service.global.UserCrudService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/global/sales-point")
@RequiredArgsConstructor
public class SalesPointController {

    private final InvitationService invitationService;
    private final AdminService adminService;
    private final TenantService tenantService;
    private final TenantSubscriptionBillService billService;
    private final UserCrudService userCrudService;

    @Operation(summary = "send invitation to the operation head")
    @PostMapping("/invite/operational-head")
    public ResponseEntity<String> inviteOperationalHead(@Valid @RequestBody InvitationRequestDto invitationRequestDto){
        return ResponseEntity.ok(invitationService.inviteOperationHead(invitationRequestDto));
    }

    @Operation(summary = "get all the tenants")
    @GetMapping("/all/tenant")
    public ResponseEntity<List<TenantResponseDto>> getAllTenants(){
        return ResponseEntity.ok(tenantService.getAll());
    }

    @Operation(summary = "get all the tenants which are under me")
    @GetMapping("/tenant-under-me")
    public ResponseEntity<List<TenantResponseDto>> getTenantUnderMe(){
        return ResponseEntity.ok(tenantService.getBySalesPoint());
    }

    @Operation(summary = "get a tenant by the id")
    @GetMapping("/tenant/{id}")
    public ResponseEntity<TenantResponseDto> getTenant(@PathVariable Long id){
        return ResponseEntity.ok(tenantService.getTenantRequestDtoById(id));
    }


    @Operation(summary = "generate bill to a tenant")
    @PostMapping("/tenant/{tenantId}/generateBill")
    public ResponseEntity<String> generateBill(@PathVariable Long tenantId){
        return ResponseEntity.ok(billService.generateBill(tenantId));
    }

    @Operation(summary = "get all the bills of a tenant")
    @GetMapping("/tenant/{id}/get-bills")
    public ResponseEntity<List<SubscriptionBillsResponseDto>> getBills(@PathVariable Long id){
        return ResponseEntity.ok(billService.getBills(id));
    }



    @Operation(summary = "change the status of the tenant ")
    @PutMapping("/{tenantName}/{status}")
    public ResponseEntity<String> activateTenant(@PathVariable String tenantName,@PathVariable TenantStatus status) {
        return ResponseEntity.ok(adminService.activateTenant(tenantName,status));
    }



    @Operation(summary = "get all the operation head with pagination")
    @GetMapping("/operation-head")
    public ResponseEntity<List<UserResponseDto>>  getAllOperationHead(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "5") int size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "true") boolean ascending,
            @RequestParam(required = false,defaultValue = "")  String search
    ){
        return ResponseEntity.ok(userCrudService.getAllOperationHead(page,size,sortBy,ascending,search));
    }

    @Operation(summary = "get the operation head by the id")
    @GetMapping("/operation-head/{id}")
    public ResponseEntity<UserResponseDto> getOperationHeadById(@PathVariable Long id){
        return ResponseEntity.ok(userCrudService.getOperationHeadById(id));
    }

    @Operation(summary = "delete operation head by id")
    @DeleteMapping("/operational-head/{id}")
    public ResponseEntity<String> deleteOperationalHead(@PathVariable Long id){
        return ResponseEntity.ok(userCrudService.deleteById(id));
    }

}
