package com.project.saas.security;


import com.project.saas.config.tenantConfig.TenantContext;
import com.project.saas.entity.master.EndUser;
import com.project.saas.entity.master.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtUtil {
    private String secretString;

    public JwtUtil(@Value("${secret}")
                     String secretString) {

        this.secretString = secretString;

    }




    public String generateToken(String email,String tenantName) {

//        Map<String, String> claims = new HashMap<>();
//        claims.put("email", email);
//        log.debug(">>>>> tenant context: {}",tenantName);
//        System.out.println(">>>>> tenant context: "+tenantName);
//        claims.put("tenant",tenantName);


        return Jwts.builder()
                .subject(email)
                .claim("tenant",tenantName)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + (1000 * 60 * 30)))
                .signWith(Keys.hmacShaKeyFor(secretString.getBytes()), Jwts.SIG.HS256)

                .compact();
    }


    public Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secretString.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String returnSubject(String token) {
        return extractClaims(token).getSubject();
    }


    public boolean validateToken(UserDetails userDetails, String token) {
        String username = extractClaims(token).getSubject();
        return (username.equals(userDetails.getUsername()) && !isExpired(token));
    }

    public boolean isExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }


}
