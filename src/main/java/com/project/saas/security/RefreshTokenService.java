package com.project.saas.security;



import com.practise.restaurant_management.entity.RefreshToken;
import com.practise.restaurant_management.entity.User;
import com.practise.restaurant_management.repo.RefreshTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepo repo;

    public String createRefreshToken(User user) {
        RefreshToken token = new RefreshToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);
        token.setExpiryDate(Instant.now().plus(7, ChronoUnit.DAYS));

        repo.save(token);
        return token.getToken();
    }
}
