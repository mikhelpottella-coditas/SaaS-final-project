package com.project.saas.controller;

import com.project.saas.annotation.TenantValid;
import com.project.saas.service.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tenant")
@TenantValid
public class TenantController {

    private final TenantService tenantService;




}
