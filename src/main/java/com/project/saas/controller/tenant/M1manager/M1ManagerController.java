package com.project.saas.controller.tenant.M1manager;


import com.project.saas.entity.tenant.TenantUser;
import com.project.saas.service.tenant.TenantUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/tenant/m1-manager/")
public class M1ManagerController {

    private final TenantUserService tenantUserService;

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/tenant-user/{id}")
    public TenantUser getTenantUser(@PathVariable Long id){
        return tenantUserService.getTenantUserById(id);
    }

}
