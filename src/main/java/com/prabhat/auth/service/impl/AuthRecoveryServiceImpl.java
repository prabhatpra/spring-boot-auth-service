package com.prabhat.auth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.prabhat.auth.dto.ForgotUsernameRequest;
import com.prabhat.auth.dto.ForgotUsernameResponse;
import com.prabhat.auth.service.interfaces.AuthRecoveryService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthRecoveryServiceImpl implements AuthRecoveryService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ForgotUsernameResponse sendUsernameToEmail(ForgotUsernameRequest request) {
        String email = request.getEmailOrUsername();
        log.info("Checking username for email: {}", email);

        try {
            // DB se username fetch karo
            String username = jdbcTemplate.queryForObject(
                    "SELECT username FROM user WHERE email = ?",
                    String.class,
                    email
            );

            log.info("Username found for {}: {}", email, username);

            // ✅ success case → 3 values bhejni hain (message + success + username)
            return new ForgotUsernameResponse(
                    true,
                    "Your username is: " + username,
                    username
            );

        } catch (EmptyResultDataAccessException e) {
            log.warn("Email not found in system: {}", email);
            return new ForgotUsernameResponse(
                    false,
                    "Email not found in system.",
                    null
            );

        } catch (Exception e) {
            log.error("Error while fetching username for email: {}", email, e);
            return new ForgotUsernameResponse(
                    false,
                    "Something went wrong.",
                    null
            );
        }
    }
}
