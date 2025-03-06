package com.hcodes.eventus.config;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;

@Component
public class JWTGenerator {

    @Value("${jwt.secret}")
    public String key;

    public String generateToken(Authentication authentication) {
        String username = authentication.getName(); 
        Date currenDate = new Date(); 
        Date expireDate = new Date(currenDate.getTime() + 70000);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8)))
                .compact();

        System.out.println("Token generated: " + token);
        return token;
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody(); 

        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts
               .parserBuilder()
               .setSigningKey(Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8)))
               .build()
               .parseClaimsJws(token); 
               return true; 
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("Token is expired or invalid"); 
        }
    }
}
