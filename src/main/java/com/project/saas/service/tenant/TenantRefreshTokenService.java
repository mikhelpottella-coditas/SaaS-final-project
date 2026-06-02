package com.project.saas.service.tenant;

import com.project.saas.config.tenantConfig.TenantContext;
import com.project.saas.entity.tenant.TenantRefreshToken;
import com.project.saas.entity.tenant.TenantUser;
import com.project.saas.exception.CustomException;
import com.project.saas.repo.tenant.TenantRefreshTokenRepo;
import com.project.saas.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TenantRefreshTokenService {
    private final TenantRefreshTokenRepo tenantRefreshTokenRepo;
    private final JwtUtil jwtUtil;


    public String createRefreshToken(TenantUser user) {
        TenantRefreshToken token = new TenantRefreshToken();
        token.setToken(UUID.randomUUID().toString());
        token.setTenantUser(user);
        token.setExpiryDate(LocalDateTime.now().plusDays(1));

        tenantRefreshTokenRepo.save(token);
        return token.getToken();
    }

    public String refresh(String refreshToken) {

        TenantRefreshToken token = tenantRefreshTokenRepo.findById(refreshToken).orElseThrow();

        if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new CustomException(HttpStatus.BAD_REQUEST,"Expired");
        }
        String tenant = TenantContext.getTenant()==null?"public":TenantContext.getTenant();

        String newAccess = jwtUtil.generateToken(token.getTenantUser().getEmail(),tenant);
        log.info("refreshing the token with the id : {}",newAccess);
        return "access token: "+newAccess;
    }


}
