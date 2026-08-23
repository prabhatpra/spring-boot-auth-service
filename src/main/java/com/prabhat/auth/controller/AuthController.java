package com.prabhat.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prabhat.auth.constants.Constants;
import com.prabhat.auth.dto.LoginRequestDTO;
import com.prabhat.auth.dto.AuthResponseDTO;
import com.prabhat.auth.dto.RegisterRequestDTO;
import com.prabhat.auth.dto.UserResponseDTO;
import com.prabhat.auth.service.interfaces.AuthService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(Constants.BASE_API)
@Slf4j
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(Constants.REGISTER_API)
    public ResponseEntity<UserResponseDTO> signup(@Valid @RequestBody RegisterRequestDTO signupRequest) {
        log.info("Signup request received for email: {}", signupRequest.getEmail());
        UserResponseDTO response = authService.signup(signupRequest);
        log.info("Signup success for username: {}", response.getUserName());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(Constants.LOGIN_API)
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        log.info("Login request received for username: {}", loginRequest.getUserName());
        AuthResponseDTO response = authService.login(loginRequest);
        log.info("Login success for userName: {}", response.getUserName());
        return ResponseEntity.ok(response);
    }
    
}











