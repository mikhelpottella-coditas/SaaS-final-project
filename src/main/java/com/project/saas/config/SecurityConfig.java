package com.project.saas.config;


import com.project.saas.config.tenantConfig.AuthTenantFilter;
import com.project.saas.config.tenantConfig.SwitchTenantFilter;
import com.project.saas.enums.Role;
import com.project.saas.security.JwtFilter;
import com.project.saas.service.CustomUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserService userService;
    private final JwtFilter jwtFilter;
    private final AuthTenantFilter authTenantFilter;
    private final SwitchTenantFilter switchTenantFilter;


    @Bean
    public AuthenticationManager authenticationManager(CustomUserService userService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(userService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(daoAuthenticationProvider);
    }

    protected static final String[] PUBLIC_URLS = {
            "/api/v1/auth/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/webjars/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/global/auth/**").permitAll()
                                .requestMatchers(PUBLIC_URLS).permitAll()
                                .requestMatchers("/tenant/auth/**").permitAll()
                                .requestMatchers("/tenant/admin/**").hasAnyRole(Role.OPERATIONAL_HEAD.name(),Role.TENANT_ADMIN.name())
                                .requestMatchers("/tenant/M1/**").hasAnyRole(Role.M1_MANAGER.name(),Role.TENANT_ADMIN.name())
                                .requestMatchers("/tenant/m2/**").hasAnyRole(Role.M2_MANAGER.name(),Role.M1_MANAGER.name(),Role.TENANT_ADMIN.name())
                                .requestMatchers("/tenant/personnel/**").hasAnyRole(Role.PERSONNEL.name(),Role.M2_MANAGER.name(),Role.M1_MANAGER.name(),Role.TENANT_ADMIN.name())


                                .requestMatchers("/global/admin/**").hasAnyRole(Role.ADMIN.name())
                                .requestMatchers("/global/management/**").hasAnyRole(Role.MANAGEMENT_STAFF.name(),Role.ADMIN.name())
                                .requestMatchers("/global/state-manager/**").hasAnyRole(Role.STATE_MANAGEMENT_STAFF.name(),Role.MANAGEMENT_STAFF.name(),Role.ADMIN.name())
                                .requestMatchers("/global/district-manager/**").hasAnyRole(Role.DISTRICT_MANAGEMENT_STAFF.name(),Role.STATE_MANAGEMENT_STAFF.name(),Role.MANAGEMENT_STAFF.name(),Role.ADMIN.name())
                                .requestMatchers("/global/city-manager/**").hasAnyRole(Role.CITY_MANAGEMENT_STAFF.name(),Role.DISTRICT_MANAGEMENT_STAFF.name(),Role.STATE_MANAGEMENT_STAFF.name(),Role.MANAGEMENT_STAFF.name(),Role.ADMIN.name())
                                .requestMatchers("/global/sales-point/**").hasAnyRole(Role.SALES_POINT.name(),Role.MANAGEMENT_STAFF.name(),Role.ADMIN.name())
                                .requestMatchers("/global/operational-head/**").hasAnyRole(Role.OPERATIONAL_HEAD.name(),Role.SALES_POINT.name(),Role.MANAGEMENT_STAFF.name(),Role.ADMIN.name())
                                .requestMatchers("/global/biller/**").hasAnyRole(Role.BILLER.name(),Role.CITY_MANAGEMENT_STAFF.name(),Role.DISTRICT_MANAGEMENT_STAFF.name(),Role.STATE_MANAGEMENT_STAFF.name(),Role.MANAGEMENT_STAFF.name(),Role.ADMIN.name())
                                .requestMatchers("/global/crm/**").hasAnyRole(Role.CMR.name(),Role.CITY_MANAGEMENT_STAFF.name(),Role.DISTRICT_MANAGEMENT_STAFF.name(),Role.STATE_MANAGEMENT_STAFF.name(),Role.MANAGEMENT_STAFF.name(),Role.ADMIN.name())
                                .requestMatchers("/global/electrician/**").hasAnyRole(Role.ELECTRICIAN.name(),Role.CITY_MANAGEMENT_STAFF.name(),Role.DISTRICT_MANAGEMENT_STAFF.name(),Role.STATE_MANAGEMENT_STAFF.name(),Role.MANAGEMENT_STAFF.name(),Role.ADMIN.name())
                                .requestMatchers("/global/customer/**").hasAnyRole(Role.CUSTOMER.name())
                                .requestMatchers("/tenant/customer/**").hasAnyRole(Role.CUSTOMER.name())
                                .requestMatchers("/global/cross/**").authenticated()
                                .requestMatchers("/tenant/user/**").authenticated()
                                .requestMatchers("/global/user/**").authenticated()
                                .requestMatchers("/user/**").authenticated()
                                .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(authTenantFilter, JwtFilter.class)
                .addFilterAfter(switchTenantFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
