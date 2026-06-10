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

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/auth/**").permitAll()
                                .requestMatchers("/operational-head/**").hasAnyRole(Role.OPERATIONAL_HEAD.name())
                                .requestMatchers("/tenant/m1-manager/**").hasAnyRole(Role.M1_MANAGER.name())
                                .requestMatchers("/user/**").authenticated()
                                .anyRequest().permitAll())
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(authTenantFilter, JwtFilter.class)
                .addFilterAfter(switchTenantFilter, UsernamePasswordAuthenticationFilter.class)
                .userDetailsService(userService);

        return http.build();
    }

}
