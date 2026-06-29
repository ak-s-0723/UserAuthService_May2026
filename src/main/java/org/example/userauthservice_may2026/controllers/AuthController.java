package org.example.userauthservice_may2026.controllers;

import org.example.userauthservice_may2026.dtos.LoginRequestDto;
import org.example.userauthservice_may2026.dtos.SignupRequestDto;
import org.example.userauthservice_may2026.dtos.UserDto;
import org.example.userauthservice_may2026.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    //signup
    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody SignupRequestDto signupRequestDto) {

    }

    //login
    @PostMapping
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto loginRequestDto) {

    }
}
