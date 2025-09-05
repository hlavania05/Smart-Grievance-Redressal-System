package com.smartgrievance.grievance_system.auth_service.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import javax.crypto.SecretKey;


import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    private final SecretKey JWT_SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS256);;
    private final long Expiration_time = 1000*60*60;
    
    public String generateToken(String email, String roleName){
        return Jwts.builder()
        .setSubject(email)
        .claim("role", roleName)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + Expiration_time))
                .signWith(JWT_SECRET, SignatureAlgorithm.HS256)
                .compact();
    }

    // Extract claims
    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(JWT_SECRET)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    
    // Validate token
    public boolean validateToken(String token, String email) {
        String subject = extractClaims(token).getSubject();
        return subject.equals(email) && !isTokenExpired(token);
    }
    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

}
