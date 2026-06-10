package com.project.saas.config.tenantConfig;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * this filter is used to switch the schema for the data accessing purpose.
 * so, once the validation is done then this filter will run
 */

@Component
public class SwitchTenantFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String tenant = request.getHeader("switchTenant");

        TenantContext.setTenant(tenant);

        filterChain.doFilter(request, response);

    }
}
