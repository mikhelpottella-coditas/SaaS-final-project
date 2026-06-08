package com.project.saas.service.tenant;

import com.project.saas.config.tenantConfig.TenantContext;
import com.project.saas.dto.global.request_dto.LoginRequestDto;
import com.project.saas.dto.global.request_dto.UserRequestDto;
import com.project.saas.entity.tenant.TenantUser;
import com.project.saas.enums.Role;
import com.project.saas.exception.CustomException;
import com.project.saas.repo.tenant.TenantUserRepo;
import com.project.saas.security.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class TenantUserService {

    private final TenantUserRepo tenantUserRepo;
    private final PasswordEncoder passwordEncoder;
    private final TenantRefreshTokenService tenantRefreshTokenService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final DataSource dataSource;



    public String save(@Valid UserRequestDto userDto) {
        try {
            System.out.println(dataSource.getConnection().getSchema());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        TenantUser user =  TenantUser.builder()
                .email(userDto.email())
                .password(passwordEncoder.encode(userDto.password()))
                .firstName(userDto.firstName())
                .lastName(userDto.lastName())
                .phone(userDto.phone())
                .role(Role.TENANT_ADMIN)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();






        tenantUserRepo.save(user);

        return "user successfully saved";

    }




    public String validateLogin(@Valid LoginRequestDto loginRequestDto) {

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequestDto.email(), loginRequestDto.password());

        Authentication authentication = authenticationManager.authenticate(token);

        if(authentication.isAuthenticated()){
            TenantUser user = (TenantUser) authentication.getPrincipal();
            String tenant = TenantContext.getTenant()==null?"public":TenantContext.getTenant();

            String result = "access token : "+ jwtUtil.generateToken(loginRequestDto.email(),tenant) + "\n refresh token : " + tenantRefreshTokenService.createRefreshToken( user) ;
            log.info("User logged in successfully");
            return result;

        }
        else throw new CustomException(HttpStatus.UNAUTHORIZED, "Invalid username or password");


    }

    public TenantUser getTenantUserById(Long id) {
        TenantContext.setTenant("tata");

        TenantUser tenantUser = tenantUserRepo.findById(id).orElse(null);

        TenantContext.clear();

        return tenantUser;

    }
}
