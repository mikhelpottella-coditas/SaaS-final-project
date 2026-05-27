package com.project.saas.security;


import com.project.saas.entity.master.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    private String secretString;

    public JwtUtil(@Value("${secret}")
                     String secretString) {

        this.secretString = secretString;

    }




    public String generateToken(User user) {

        Map<String, String> claims = new HashMap<>();
        claims.put("email", user.getEmail());
        claims.put("username", user.getEmail());

        String tenant = user.getTenant()==null?"public":user.getTenant().getName();


        return Jwts.builder()
                .claims()
                .subject(user.getEmail())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + (1000 * 60 * 30)))
                .and()
                .claim("tenant", tenant)
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
