package com.firm.brokage.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class TokenManager {

    private static final int validity = 30 * 60 * 1000;
    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(String username){
        LocalDateTime localDateTime = LocalDateTime.now();
        Date current = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Date expiration = Date.from(localDateTime.plusMinutes(validity).atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setSubject(username)
                .setIssuer("https://test.firm.brokage.com.tr")
                .setIssuedAt(current)
                .setExpiration(expiration)
                .signWith(key)
                .compact();
    }
    public boolean tokenValidate(String token){
        if(getUsernameToken(token) != null && isExpired(token)){
            return true;
        }
        return false;
    }

    public String getUsernameToken(String token) {
        Claims claims= getClaims(token);
        return claims.getSubject();
    }
    public boolean isExpired(String token) {
        Claims claims= getClaims(token);
        return claims.getExpiration().after(new Date(System.currentTimeMillis()));
    }
    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    }
}
