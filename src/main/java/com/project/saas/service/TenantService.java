package com.project.saas.service;

import com.project.saas.dto.global.request_dto.InvitationRequestDto;
import com.project.saas.dto.global.request_dto.TenantRequestDto;
import com.project.saas.dto.global.responceDto.TenantResponseDto;
import com.project.saas.entity.master.*;
import com.project.saas.enums.TenantStatus;
import com.project.saas.exception.CustomException;
import com.project.saas.repo.global.StateRepo;
import com.project.saas.repo.global.TenantRepo;
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
import java.util.List;

@Service
@RequiredArgsConstructor
public class TenantService {


    private static final Logger log = LogManager.getLogger(TenantService.class);
    private final JdbcTemplate jdbcTemplate;
    private final UserService userService;
    private final DataSource dataSource;
    private final TenantRepo tenantRepo;
    private final InvitationService invitationService;
    private final StateRepo stateRepo;


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


    public Tenant getById(Long id){
        return tenantRepo.findById(id).orElseThrow(()-> new CustomException(HttpStatus.BAD_REQUEST,"Tenant not found"));
    }


    public String tenantRegistration(@Valid TenantRequestDto tenantRequestDto) {

        if (tenantRepo.existsTenantByName(tenantRequestDto.name()))
            throw new CustomException(HttpStatus.ALREADY_REPORTED, "the provider is already exists");

        User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(() ->
                new CustomException(HttpStatus.UNAUTHORIZED, "the user is not authorized"));

        createSchema(tenantRequestDto.name());
        runMigration(tenantRequestDto.name());

        User salesPoint = userService.findById(tenantRequestDto.salesPointId());

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
        OperatingTenant operatingTenant = OperatingTenant.builder().user(user).tenant(tenant).salesPoint(salesPoint).build();

        tenant.setOperatingTenant(operatingTenant);

        tenantRequestDto.availableStatesList().forEach(state -> {
            TenantAvailableStates tenantAvailableStates = TenantAvailableStates.builder().availableState(state).build();
            tenant.addState(tenantAvailableStates);
        });


        tenantRepo.save(tenant);



        InvitationRequestDto invitationRequestDto = new InvitationRequestDto(user.getEmail(), "welcome to the application. please register yourself as an admin with the following link: ");

       String inviteResponse =  invitationService.inviteOperationHeadAsAdmin(invitationRequestDto);
        log.info(inviteResponse);

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

    public Tenant getByName(String tenant) {
        return tenantRepo.findTenantByName(tenant).orElseThrow(()->new CustomException(HttpStatus.NOT_FOUND, "the tenant not found"));
    }

    public void save(Tenant tenant) {
        tenantRepo.save(tenant);
    }

    public List<TenantResponseDto> getAll() {
        List<Tenant> tenants = tenantRepo.findAll();
        return tenants.stream().map(tenant -> new TenantResponseDto(tenant.getId(), tenant.getName(), tenant.getSchemaName(), tenant.getTenantStatus(), tenant.getCreatedAt(), tenant.getUpdatedAt(), tenant.getSubscriptionAmount(), tenant.getOperatingTenant().getUser().getId())).toList();
    }

    public TenantResponseDto getTenantRequestDtoById(Long id) {
        Tenant tenant = getById(id);
        return new TenantResponseDto(tenant.getId(), tenant.getName(),
                tenant.getSchemaName(), tenant.getTenantStatus(),
                tenant.getCreatedAt(), tenant.getUpdatedAt(),
                tenant.getSubscriptionAmount(),
                tenant.getOperatingTenant().getUser().getId());
    }

    public List<TenantResponseDto> getBySalesPoint() {
        User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(()-> new CustomException(HttpStatus.UNAUTHORIZED, "the user is not authorized"));
        List<Tenant> tenantList = tenantRepo.findTenantByOperatingTenant_SalesPoint_Id(user.getId());
        return tenantList.stream().map(tenant -> new TenantResponseDto(tenant.getId(), tenant.getName(), tenant.getSchemaName(), tenant.getTenantStatus(), tenant.getCreatedAt(), tenant.getUpdatedAt(), tenant.getSubscriptionAmount(), tenant.getOperatingTenant().getUser().getId())).toList();
    }

}
