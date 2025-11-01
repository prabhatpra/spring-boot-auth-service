package com.prabhat.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prabhat.auth.dto.ForgotUsernameRequest;
import com.prabhat.auth.dto.ForgotUsernameResponse;
import com.prabhat.auth.service.interfaces.AuthRecoveryService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/reset")
@Slf4j
public class AuthRecoveryController {

    private final AuthRecoveryService authRecoveryService;

    public AuthRecoveryController(AuthRecoveryService authRecoveryService) {
        this.authRecoveryService = authRecoveryService;
    }

    @PostMapping("/send-username")
    public ResponseEntity<ForgotUsernameResponse> sendUsernameToEmail(@RequestBody ForgotUsernameRequest request) {
        log.info("Received username recovery request for: {}", request.getEmailOrUsername());
        ForgotUsernameResponse response = authRecoveryService.sendUsernameToEmail(request);
        return ResponseEntity.ok(response);
    }
}

