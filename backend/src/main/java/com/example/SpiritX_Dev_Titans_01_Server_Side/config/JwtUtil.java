package com.example.SpiritX_Dev_Titans_01_Server_Side.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final long EXPIRATION_TIME = 86400000; // 1 day validity in milliseconds

    // Method to generate a secure signing key for HS256
    private Key getSigningKey() {
        // Generates a secure 256-bit key for HS256
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    // Method to generate a JWT token for the given username
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) // Set the subject (user)
                .setIssuedAt(new Date()) // Set issue time
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Set expiration time
                .signWith(getSigningKey()) // Sign with the generated secure key
                .compact();
    }

    // Method to extract the username from the JWT token
    public String extractUsername(String token) {
        return getClaims(token).getSubject(); // Extract subject (username) from claims
    }

    // Method to parse the JWT token and return the claims
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey()) // Use secure signing key for verification
                .build()
                .parseClaimsJws(token) // Parse JWT and extract claims
                .getBody();
    }

    // Method to validate the JWT token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey()) // Validate with secure key
                    .build()
                    .parseClaimsJws(token); // Parse JWT and check validity
            return true; // Token is valid
        } catch (Exception e) {
            return false; // Token is invalid or expired
        }
    }
}
