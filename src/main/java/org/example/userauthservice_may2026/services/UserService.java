package org.example.userauthservice_may2026.services;

import org.example.userauthservice_may2026.models.User;
import org.example.userauthservice_may2026.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public User getUserDetails(Long userId) {
        Optional<User> userOptional = userRepo.findById(userId);
        if(userOptional.isPresent()) {
            return userOptional.get();
        }

        return null;
    }
}
