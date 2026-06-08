package com.project.saas.controller.tenant.admin;

import com.project.saas.dto.tenant.request.MeterRequestDto;
import com.project.saas.dto.tenant.response.MeterResponseDto;
import com.project.saas.service.tenant.TenantMeterService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tenant/admin")
public class AdminController {

    private
    TenantMeterService meterService;

    @PostMapping("/meters")
    public ResponseEntity<MeterResponseDto> addMeter(@Valid @RequestBody MeterRequestDto meterRequestDto) {
        return ResponseEntity.status(201).build();
    }

}
