package com.example.inventory.controller;

import com.example.inventory.model.AuthRequest;

import com.example.inventory.model.AuthResponse;
import com.example.inventory.model.User;
import com.example.inventory.repository.UserRepository;
import com.example.inventory.security.JwtTokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private AuthenticationManager authManager;
    @Autowired private JwtTokenProvider jwtProvider;
    @Autowired private UserRepository repo;
    @Autowired private PasswordEncoder encoder;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User u) {
        if (repo.findByUsername(u.getUsername()).isPresent())
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");

        u.setPassword(encoder.encode(u.getPassword()));
        
        // ✅ Ensure role starts with "ROLE_"
        String role = u.getRole() == null ? "ROLE_USER" : u.getRole();
        if (!role.startsWith("ROLE_")) {
            role = "ROLE_" + role;
        }
        u.setRole(role);

        repo.save(u);
        return ResponseEntity.status(HttpStatus.CREATED).body("Registered");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest r) {
        try {
            Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(r.getUsername(), r.getPassword())
            );
            return ResponseEntity.ok(new AuthResponse(jwtProvider.generateToken(auth)));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
