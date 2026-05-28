package com.project.saas.service;

import com.project.saas.dto.request_dto.TenantRequestDto;
import com.project.saas.entity.master.OperatingTenant;
import com.project.saas.entity.master.Tenant;
import com.project.saas.entity.master.User;
import com.project.saas.enums.TenantStatus;
import com.project.saas.exception.CustomException;
import com.project.saas.repo.TenantRepo;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.flywaydb.core.Flyway;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class TenantService {


    private static final Logger log = LogManager.getLogger(TenantService.class);
    private final JdbcTemplate jdbcTemplate;
    private final UserService userService;
    private final DataSource dataSource;
    private final TenantRepo tenantRepo;


    public void addTenant(String tenantName) {
        createSchema(tenantName);
        runMigration(tenantName);
    }

    public void createSchema(String schema) {
        jdbcTemplate.execute(
                "CREATE SCHEMA " + schema
        );
    }

    public void runMigration(String schema) {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .schemas(schema)
                .locations("classpath:/db/migration/tenant")
                .baselineOnMigrate(true)
                .load();

        flyway.migrate();
    }


    public String tenantRegistration(@Valid TenantRequestDto tenantRequestDto) {

        if (tenantRepo.existsTenantByName(tenantRequestDto.name()))
            throw new CustomException(HttpStatus.ALREADY_REPORTED, "the provider is already exists");

        User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(() ->
                new CustomException(HttpStatus.UNAUTHORIZED, "the user is not authorized"));


        log.info("Creating tenant by the operational head : {}", user.getUsername());
        Tenant tenant = Tenant.builder()
                .name(tenantRequestDto.name())
                .tenantStatus(TenantStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .schemaName(tenantRequestDto.name())
                .subscriptionAmount(tenantRequestDto.subscriptionAmount())
                .build();


        log.info("setting the operation head to the tenant");
        OperatingTenant operatingTenant = OperatingTenant.builder().user(user).tenant(tenant).build();

        tenant.setOperatingTenant(operatingTenant);

        tenantRepo.save(tenant);

        this.addTenant(tenant.getSchemaName());

        return "Schema registered successfully";

    }

    public String update(TenantRequestDto tenantRequestDto, Long id) {
        Tenant tenant = tenantRepo.findById(id).orElseThrow(()-> new CustomException(HttpStatus.NOT_FOUND, "the tenant not found"));

        tenant.setUpdatedAt(LocalDateTime.now());
        tenant.setSubscriptionAmount(tenantRequestDto.subscriptionAmount());

        tenantRepo.save(tenant);
        log.info("the tenant with id : {} id updated successfully", tenant.getId());
        return "tenant update successful";
    }
}
