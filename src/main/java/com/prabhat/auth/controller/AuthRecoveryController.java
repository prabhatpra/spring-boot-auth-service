package com.prabhat.auth.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prabhat.auth.recoverydto.ForgotPasswordRequest;
import com.prabhat.auth.recoverydto.ResetPasswordRequest;
import com.prabhat.auth.service.interfaces.AuthRecoveryService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthRecoveryController {

    private final AuthRecoveryService authRecoveryService;

    public AuthRecoveryController(AuthRecoveryService authRecoveryService) {
        this.authRecoveryService = authRecoveryService;
    }

    // Forgot Password
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest request) {

        authRecoveryService.forgotPassword(request.getEmail());

        return ResponseEntity.ok("Reset link sent if email exists");
    }
    
    // Reset Password
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request){
    	 
    	authRecoveryService.resetPassword(request.getToken(), request.getNewPassword());
    	
    	return ResponseEntity.ok("Password reset successful");
    }
}