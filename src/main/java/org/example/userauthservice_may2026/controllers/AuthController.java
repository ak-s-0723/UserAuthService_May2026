package org.example.userauthservice_may2026.controllers;

import org.antlr.v4.runtime.misc.Pair;
import org.example.userauthservice_may2026.dtos.LoginRequestDto;
import org.example.userauthservice_may2026.dtos.SignupRequestDto;
import org.example.userauthservice_may2026.dtos.UserDto;
import org.example.userauthservice_may2026.dtos.ValidateTokenRequestDto;
import org.example.userauthservice_may2026.exceptions.PasswordMismatchException;
import org.example.userauthservice_may2026.exceptions.UserAlreadyExistsException;
import org.example.userauthservice_may2026.exceptions.UserNotSignedUpException;
import org.example.userauthservice_may2026.models.Role;
import org.example.userauthservice_may2026.models.User;
import org.example.userauthservice_may2026.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    //signup
    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody SignupRequestDto signupRequestDto) {
        try {
            User user = authService.signup(signupRequestDto.getName(), signupRequestDto.getEmail(), signupRequestDto.getPassword());
            return new ResponseEntity<>(from(user), HttpStatus.CREATED);
        } catch (Exception exception) {
            throw exception;
         //  return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }

    }

    //login
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            Pair<User,String> response = authService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
            User user = response.a;
            String token = response.b;
            MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
            headers.add(HttpHeaders.SET_COOKIE, token);
            headers.add("On special Request of","vashisht");
            return new ResponseEntity<>(from(user),headers,HttpStatus.OK);
        } catch (Exception exception) {
            throw exception;
        }
    }


    @PostMapping("/validateToken")
    public Boolean validateToken(@RequestBody ValidateTokenRequestDto validateTokenRequestDto) {
        return authService.validateToken(validateTokenRequestDto.getToken());
    }

    public UserDto from(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setId(user.getId());
        List<String> roles = new ArrayList<>();
        for(Role role : user.getRoles()) {
            roles.add(role.getValue());
        }
        userDto.setRoles(roles);
        return userDto;
    }
}
