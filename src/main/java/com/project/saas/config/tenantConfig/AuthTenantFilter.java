package com.project.saas.config.tenantConfig;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * this filter is used to switch the tenant to validate the user.
 * so based on where the user is we will switch to that tenant
 */

@Component
public class AuthTenantFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String tenant = request.getHeader("authTenant");

        TenantContext.setTenant(tenant);

        filterChain.doFilter(request, response);

    }
}
