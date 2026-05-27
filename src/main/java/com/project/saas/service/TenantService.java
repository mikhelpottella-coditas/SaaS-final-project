package com.project.saas.service;

import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
@RequiredArgsConstructor
public class TenantService {


    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

    public String addTenant(String tenantName) {
        createSchema(tenantName);
        runMigration(tenantName);

        return "Schema created successfully";
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
}
