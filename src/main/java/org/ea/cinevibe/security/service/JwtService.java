package org.ea.cinevibe.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.expireTime}")
    private Long expireTime;

    @Value("${jwt.secretKey}")
    private String secretKey;

    public String generateToken(String username) {
        return Jwts.builder().
                subject(username).
                issuedAt(new Date(System.currentTimeMillis())).
                expiration(new Date(System.currentTimeMillis() + expireTime)).
                signWith(getSecretKey(secretKey)).
                compact();
    }

    private SecretKey getSecretKey(String secretKey) {
        byte[] decode = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(decode);
    }

    public boolean isValidToken(String token) {
        try {
            return extractClaims(token).
                    getExpiration().
                    after(new Date(System.currentTimeMillis()));
        } catch (Exception e) {
            return false;
        }
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    private Claims extractClaims(String token) {
        try {
            return Jwts.
                    parser().
                    verifyWith(getSecretKey(secretKey)).
                    build().
                    parseSignedClaims(token).
                    getPayload();
        } catch (Exception e) {
            throw new IllegalArgumentException("Token is not valid or expired: " + e.getMessage());
        }
    }
}
