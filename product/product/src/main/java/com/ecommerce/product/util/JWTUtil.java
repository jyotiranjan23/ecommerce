package com.ecommerce.product.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtil {
    private final SecretKey key;

    public JWTUtil(@Value("${JWT_SECRET}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
    
    public String extractUserName(String token){
        return extractClaims(token).getSubject();
    }
    public Claims extractClaims(String token){
        Claims body = Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody();
        return body;
        
    }
    public boolean validateToken(String userName, UserDetails userDetails, String token) {
        return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
            
        
    }
     private boolean isTokenExpired(String token) {
        return extractClaims(token)
        .getExpiration()
        .before(new Date());
    }



}
