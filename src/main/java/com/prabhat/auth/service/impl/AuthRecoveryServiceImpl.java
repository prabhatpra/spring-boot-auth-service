package com.prabhat.auth.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prabhat.auth.entity.ResetToken;
import com.prabhat.auth.entity.User;
import com.prabhat.auth.exception.AuthException;
import com.prabhat.auth.repository.ResetTokenRepository;
import com.prabhat.auth.repository.UserRepository;
import com.prabhat.auth.service.interfaces.AuthRecoveryService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthRecoveryServiceImpl implements AuthRecoveryService {

    private final UserRepository userRepository;
    private final ResetTokenRepository resetTokenRepository;
    private final PasswordEncoder passwordEncoder;
    
    AuthRecoveryServiceImpl(UserRepository userRepository,
            ResetTokenRepository resetTokenRepository, 
            PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.resetTokenRepository = resetTokenRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    @Transactional
    public void forgotPassword(String email) {
       
        log.info("Forgot password request received for email: {}", email);

        Optional<User> userOpt = userRepository.findByEmail(email);

        if(userOpt.isEmpty()) {
            log.warn("Forgot password requested for non-existing email: {}", email);
            return; // security reason
        }
        
        User user = userOpt.get();
        
        String token = UUID.randomUUID().toString();
        log.info("Reset token generated for user: {}", user.getEmail());
        
        ResetToken resetToken = new ResetToken();
        
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(15));
        
        resetTokenRepository.deleteByUser(user);
        log.info("Old reset tokens deleted for user: {}", user.getEmail());
        
        resetTokenRepository.save(resetToken);
        log.info("New reset token saved for user: {}", user.getEmail());
        
        // For testing (later replace with email service)
        log.info("Reset link generated (for testing): http://localhost:8080/reset-password?token={}", token);
    }
    
    // ResetPassword
    @Override
    @Transactional
    public void resetPassword(String token, String newPassword) {
        
        log.info("Reset password request received for token: {}", token);

        ResetToken resetToken = resetTokenRepository.findByToken(token)
                .orElseThrow(() -> {
                    log.error("Invalid reset token used: {}", token);
                    return new AuthException.InvalidTokenException("Invalid token");
                });
        
        if(resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            log.warn("Expired token used for user: {}", resetToken.getUser().getEmail());
            throw new AuthException.InvalidTokenException("Token Expired");
        }
        
        User user = resetToken.getUser();
        
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        log.info("Password reset successful for user: {}", user.getEmail());
        
        resetTokenRepository.delete(resetToken);
        log.info("Reset token deleted for user: {}", user.getEmail());
    }
}