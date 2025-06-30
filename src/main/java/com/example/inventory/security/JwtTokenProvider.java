package com.example.inventory.security;

import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long validityMs;

    private final UserDetailsService uds;

    public JwtTokenProvider(UserDetailsService uds) {
        this.uds = uds;
    }

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String generateToken(Authentication auth) {
        Claims claims = Jwts.claims().setSubject(auth.getName());
        claims.put("roles", auth.getAuthorities());

        Date now = new Date();
        Date exp = new Date(now.getTime() + validityMs);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Authentication getAuth(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        String username = claims.getSubject();
        Object roles = claims.get("roles");

        UserDetails userDetails = uds.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}