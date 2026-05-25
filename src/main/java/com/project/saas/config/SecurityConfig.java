package com.project.saas.config;




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

    private final UserService userService;
    private final JwtFilter jwtFilter;


    protected static final String[] PUBLIC_URLS = {
            "/api/v1/auth/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/webjars/**"
    };

    @Bean
    public AuthenticationManager authenticationManager(UserService userService, PasswordEncoder passwordEncoder){

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(userService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(daoAuthenticationProvider);

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
        http.csrf(csrf->csrf.disable())
                .authorizeHttpRequests(auth->
                        auth.requestMatchers("/auth/**").permitAll()
                                .requestMatchers(PUBLIC_URLS).permitAll()
                                .requestMatchers("/admin/**").hasAnyRole(Role.SUPER_ADMIN.toString())
                                .requestMatchers("/owner/**").hasAnyRole(Role.OWNER.toString())
                                .requestMatchers("/manager/**").hasAnyRole(Role.MANAGER.toString(),Role.OWNER.toString())
                                .requestMatchers("/kitchen/**").hasAnyRole(Role.HEAD_CHEF.toString(),Role.MANAGER.toString(),Role.OWNER.toString())
                                .requestMatchers("/staff/**").hasAnyRole(Role.WAITER.toString(),Role.MANAGER.toString(),Role.OWNER.toString())
                                .requestMatchers("/store/**").hasAnyRole(Role.STORE_KEEPER.toString(),Role.MANAGER.toString(),Role.OWNER.toString())
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .userDetailsService(userService);

        return http.build();
    }

}
