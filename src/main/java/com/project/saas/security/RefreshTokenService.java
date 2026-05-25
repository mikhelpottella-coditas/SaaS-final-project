package com.project.saas.security;



import com.project.saas.entity.master.RefreshToken;
import com.project.saas.entity.master.User;
import com.project.saas.repo.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {


    private final RefreshTokenRepository repo;

    public String createRefreshToken(User user) {
        RefreshToken token = new RefreshToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);
        token.setExpiryDate(Instant.now().plus(7, ChronoUnit.DAYS));

        repo.save(token);
        return token.getToken();
    }
}
