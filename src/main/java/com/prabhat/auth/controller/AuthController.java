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
import com.prabhat.auth.dto.LoginRequest;
import com.prabhat.auth.dto.LoginResponse;
import com.prabhat.auth.dto.SignupRequest;
import com.prabhat.auth.dto.SignupResponse;
import com.prabhat.auth.service.interfaces.AuthService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "${frontend.url}")
@RequestMapping(Constants.BASE_API)
@Slf4j
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(Constants.REGISTER_API)
    public ResponseEntity<SignupResponse> signup(@Valid @RequestBody SignupRequest signupRequest) {
        log.info("Signup request received for email: {}", signupRequest.getEmail());
        SignupResponse response = authService.signup(signupRequest);
        log.info("Signup success for userId: {}", response.getUserName());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(Constants.LOGIN_API)
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("Login request received for username: {}", loginRequest.getUserName());
        LoginResponse response = authService.login(loginRequest);
        log.info("Login success for userName: {}", response.getUserName());
        return ResponseEntity.ok(response);
    }
}
