package com.microservice.shortlink.services;

import com.microservice.shortlink.entities.User;
import com.microservice.shortlink.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthService {
    private UserRepository userRepository;

    public User getCurrentUser() {
        var authentification = SecurityContextHolder.getContext().getAuthentication();
        var userId = (Long) authentification.getPrincipal();

        return userRepository.findById(userId).orElse(null);
    }
}
