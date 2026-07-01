package org.example.userauthservice_may2026.services;

import org.example.userauthservice_may2026.exceptions.PasswordMismatchException;
import org.example.userauthservice_may2026.exceptions.UserAlreadyExistsException;
import org.example.userauthservice_may2026.exceptions.UserNotSignedUpException;
import org.example.userauthservice_may2026.models.Role;
import org.example.userauthservice_may2026.models.State;
import org.example.userauthservice_may2026.models.User;
import org.example.userauthservice_may2026.repos.RoleRepo;
import org.example.userauthservice_may2026.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User signup(String name,String email,String password) {
        Optional<User> userOptional = userRepo.findByEmail(email);
        if (userOptional.isPresent()) {
           throw new UserAlreadyExistsException("Email already exists. Please use different email Id");
        }

        User user = new User();
        user.setEmail(email);
        user.setName(name);
       // user.setPassword(password); //hash this password
        user.setPassword(bCryptPasswordEncoder.encode(password));

        Role role = null;
        Optional<Role> roleOptional = roleRepo.findByValue("NON_ADMIN");
        if (roleOptional.isEmpty()) {
            role = new Role();
            role.setValue("NON_ADMIN");
            roleRepo.save(role);
        } else {
            role = roleOptional.get();
        }

        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
        return userRepo.save(user);
    }

    public User login(String email,String password) {
        Optional<User> userOptional = userRepo.findByEmail(email);
        if (userOptional.isEmpty() || userOptional.get().getState().equals(State.DELETED)) {
          throw new UserNotSignedUpException("Please signup first !!");
        }

        User user = userOptional.get();
      //  if (!user.getPassword().equals(password)) {
        if (!bCryptPasswordEncoder.matches(password,user.getPassword())) {
           throw new PasswordMismatchException("Passwords don't match");
        }

        //token generation on wed

        return user;
    }

}

//signup
//pass = "anurag"
//bcrypt.encode("anurag") -> "AKAKAKA"
//
//login
//pass  = "anurag"
//bcrypt.encode("anurag") -> "BABABABA"

