package com.example.inventory.service;

import com.example.inventory.model.User;
import com.example.inventory.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService { 
    @Autowired private UserRepository userRepo;
    @Autowired private PasswordEncoder passwordEncoder;

    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
       
        return userRepo.save(user);
        
        
    }
}