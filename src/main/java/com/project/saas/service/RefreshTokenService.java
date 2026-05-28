package com.project.saas.service;

import com.project.saas.entity.master.RefreshToken;
import com.project.saas.entity.master.User;
import com.project.saas.repo.RefreshTokenRepository;
import com.project.saas.security.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
        token.setExpiryDate(Instant.now().plus(7, ChronoUnit.DAYS));

        refreshTokenRepository.save(token);
        return token.getToken();
    }

    public String refresh(String refreshToken) {

        RefreshToken token = refreshTokenRepository.findById(refreshToken)
                .orElseThrow();

        if (token.getExpiryDate().isBefore(Instant.now())) {
            throw new RuntimeException("Expired");
        }
        String newAccess = jwtUtil.generateToken(token.getUser()    );
        log.info("refreshing the token with the id : {}",newAccess);
        return "access token: "+newAccess;
    }
}
