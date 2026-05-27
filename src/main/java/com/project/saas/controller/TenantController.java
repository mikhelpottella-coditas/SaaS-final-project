package com.project.saas.controller;

import com.project.saas.service.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tenant")
public class TenantController {

    private final TenantService tenantService;

    @PostMapping("/{tenantName}")
    public ResponseEntity<String> addTenant(@PathVariable String tenantName) {
        return ResponseEntity.ok(tenantService.addTenant(tenantName));
    }

}
