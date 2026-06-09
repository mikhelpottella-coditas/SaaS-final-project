package com.project.saas.controller.global;

import com.project.saas.dto.global.request_dto.CustomerTenantRequestDto;
import com.project.saas.dto.global.responceDto.ElectricianResponseDto;
import com.project.saas.dto.tenant.request.TenantCustomerMeterRequestDto;
import com.project.saas.dto.tenant.response.MeterResponseDto;
import com.project.saas.service.global.ElectricianService;
import com.project.saas.service.tenant.TenantCustomerMeterService;
import com.project.saas.service.tenant.TenantMeterService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/global/cross")
public class CrossControllers {

    private final ElectricianService electricianService;
    private final TenantMeterService tenantMeterService;
    private final TenantCustomerMeterService tenantCustomerMeterService;


    // used by personnel
    @Operation(
            summary = "get all the electricians working in a particular area"
    )
    @GetMapping("/area/{areaId}/electricians")
    public ResponseEntity<List<ElectricianResponseDto>> getElectricianByArea(
            @PathVariable Long areaId,
            @RequestParam(required = false,defaultValue = "0") int page,
            @RequestParam(required = false,defaultValue = "5") int size,
            @RequestParam(required = false,defaultValue = "id") String sortBy,
            @RequestParam(required = false,defaultValue = "true") boolean ascending,
            @RequestParam(required = false,defaultValue = "") String search
    ){
        return ResponseEntity.ok(electricianService.getAllByArea(areaId,page,size,sortBy,ascending,search));
    }


    @GetMapping("/meters")
    public ResponseEntity<List<MeterResponseDto>> getMeters(){
        return ResponseEntity.ok(tenantMeterService.getAll());
    }

    @PostMapping("/onborad/customer")
    public ResponseEntity<String > onBoardCustomer(@Valid @RequestBody TenantCustomerMeterRequestDto tenantCustomerMeterRequestDto){
        return ResponseEntity.status(201).body(tenantCustomerMeterService.register(tenantCustomerMeterRequestDto));
    }



}
