package com.project.saas.config.tenantConfig;


import lombok.RequiredArgsConstructor;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class SchemaMultiTenantConnectionProvider implements MultiTenantConnectionProvider<String> {
    private final DataSource dataSource;

    @Override
    public Connection getAnyConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public Connection getConnection(String tenantIdentifier) throws SQLException {
        Connection connection = getAnyConnection();
        try {
            connection.setSchema(tenantIdentifier);
        } catch (SQLException exception) {
            throw new SQLException("There is an SQL exception occurred.");
        }
        return connection;
    }

    @Override
    public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
        try {
            connection.createStatement()
                    .execute("USE " + tenantIdentifier);
        } catch (SQLException exception) {
            throw new SQLException("There is an SQL exception occurred at releaseConnection() method execution");
        }
        connection.close();
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @Override
    public boolean isUnwrappableAs(Class<?> unwrapType) {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> unwrapType) {
        return null;
    }
}







