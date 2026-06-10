package com.project.saas.aop;


import com.project.saas.config.tenantConfig.TenantContext;
import com.project.saas.entity.master.Tenant;
import com.project.saas.enums.TenantStatus;
import com.project.saas.repo.global.TenantRepo;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class TenantValidAop {

    private final TenantRepo tenantRepo;


    @Around("@annotation(com.project.saas.annotation.TenantValid) || @within(com.project.saas.annotation.TenantValid)")
    public Object allowAccess(ProceedingJoinPoint joinPoint) throws Throwable {
        String name = TenantContext.getTenant();


        Tenant tenant = tenantRepo.findBySchemaName(name).orElse(null);

        if(tenant == null){
           return joinPoint.proceed();
        } else if (tenant.getTenantStatus() == TenantStatus.ACTIVE) {
            return joinPoint.proceed();
        }else {
            throw new RuntimeException("please pay the bills");
        }
    }
}