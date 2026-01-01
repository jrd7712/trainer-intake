package com.example.trainerintake.service;

import com.example.trainerintake.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private static final Logger log = LoggerFactory.getLogger(JwtService.class);

    private final SecretKey secretKey;
    private final long jwtExpirationMs = 1000 * 60 * 60; // 1 hour

    // Inject secret from application.properties
    public JwtService(@Value("${jwt.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * Generate a JWT containing both email (subject) and userId (claim).
     */
    public String generateToken(User user) {
        log.debug("Generating JWT for user: {} (id={})", user.getEmail(), user.getId());

        return Jwts.builder()
                .setSubject(user.getEmail())                 // email as subject
                .claim("userId", user.getId())               // add userId claim
                .setIssuedAt(new Date())                     // issue time
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extract email (subject) from token.
     */
    public String extractUsername(String token) {
        try {
            String username = parseClaims(token).getSubject();
            log.debug("Extracted username from JWT: {}", username);
            return username;
        } catch (JwtException e) {
            log.error("Failed to extract username from token: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Extract userId claim from token.
     */
    public Integer extractUserId(String token) {
        try {
            Integer userId = parseClaims(token).get("userId", Integer.class);
            log.debug("Extracted userId from JWT: {}", userId);
            return userId;
        } catch (JwtException e) {
            log.error("Failed to extract userId from token: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Validate token against UserDetails.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            final String username = extractUsername(token);
            boolean valid = (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
            log.debug("Token validation result for {}: {}", username, valid);
            return valid;
        } catch (JwtException e) {
            log.warn("Token validation failed: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Check expiration.
     */
    private boolean isTokenExpired(String token) {
        Date expiration = parseClaims(token).getExpiration();
        boolean expired = expiration.before(new Date());
        if (expired) {
            log.warn("JWT expired at {}", expiration);
        }
        return expired;
    }

    /**
     * Helper: parse claims safely.
     */
    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}