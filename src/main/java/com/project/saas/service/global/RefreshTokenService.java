package com.project.saas.service.global;

import com.project.saas.config.tenantConfig.TenantContext;
import com.project.saas.entity.master.RefreshToken;
import com.project.saas.entity.master.User;
import com.project.saas.repo.global.RefreshTokenRepository;
import com.project.saas.security.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;


    public String createRefreshToken(User user) {
        RefreshToken token = new RefreshToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);
        token.setExpiryDate(LocalDateTime.now().plusDays(1));

        refreshTokenRepository.save(token);
        return token.getToken();
    }

    public String refresh(String refreshToken) {

        RefreshToken token = refreshTokenRepository.findById(refreshToken)
                .orElseThrow();

        String tenant = TenantContext.getTenant()==null?"public":TenantContext.getTenant();
        if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Expired");
        }
        String newAccess = jwtUtil.generateToken(token.getUser().getEmail(),tenant );
        log.info("refreshing the token with the id : {}",newAccess);
        return "access token: "+newAccess;
    }
}
