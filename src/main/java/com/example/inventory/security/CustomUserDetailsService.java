package com.example.inventory.security;

import com.example.inventory.model.User;
import com.example.inventory.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Loads the user by username and builds a Spring Security UserDetails object.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch user from database
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Return UserDetails implementation with username, password, and authorities
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(), // must be BCrypt-hashed
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole().toUpperCase()))
        );
    }
}