package com.prabhat.aouth.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.prabhat.aouth.dto.LoginDTO;
import com.prabhat.aouth.dto.LoginResponse;
import com.prabhat.aouth.dto.SignupDTO;
import com.prabhat.aouth.dto.SignupResponse;
import com.prabhat.aouth.exception.AuthException;
import com.prabhat.aouth.pojo.User;
import com.prabhat.aouth.service.interfaces.AuthService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

	 // Dummy in-memory store for registered users
    private List<User> userStore = new ArrayList<>();
    private final BCryptPasswordEncoder passwordEncoder;
    
     AuthServiceImpl(BCryptPasswordEncoder passwordEncoder) {
    	this.passwordEncoder = passwordEncoder;
    }

    @Override
    public SignupResponse signup(SignupDTO signupDTO) {
        log.info("Processing signup for email: {}", signupDTO.getEmail());
        
        // 1. Validate password match
        if (!signupDTO.getPassword().equals(signupDTO.getConfirmPassword())) {
            log.error("Password mismatch for email: {}", signupDTO.getEmail());
            throw new AuthException.PasswordMismatchException("Password and confirm password do not match");
        }
        
        // 2. Check for duplicate user (by email)
        boolean exists = userStore.stream()
                .anyMatch(user -> user.getEmail().equalsIgnoreCase(signupDTO.getEmail()));
        if (exists) {
            log.error("User with email {} already exists.", signupDTO.getEmail());
            throw new AuthException.UserAlreadyExistsException("User with this email already exists");
        }
        
        String hashedPassword = passwordEncoder.encode(signupDTO.getPassword());
        log.info("Password hashed for email {}: {}", signupDTO.getEmail(), hashedPassword);

        // 4. Create new User object and store
        User user = new User(signupDTO.getUserName(), signupDTO.getEmail(), hashedPassword, "ROLE_USER");
        user.setRole("ROLE_USER");
        
        // 5. Add to in-memory store
        userStore.add(user);
        log.info("User registered successfully: {}", signupDTO.getEmail());

        // 6. Return response (for testing: include hashed password)
        SignupResponse response = new SignupResponse(
                user.getUserName(),
                user.getEmail(),
                user.getPassword() // or hashedPassword
        );

        return response;
    }
    
    @Override
    public LoginResponse login(LoginDTO loginDTO) {
    	    LoginResponse response = new LoginResponse();

    	    // 1. Search user by userName only
    	    User user = userStore.stream()
    	            .filter(u -> u.getUserName().equals(loginDTO.getUserName()))
    	            .findFirst()
    	            .orElse(null);

    	    if (user == null) {
    	        response.setMessage("User not found. Please register.");
    	        response.setSuccess(false);
    	        response.setResetPasswordLink("https://yourdomain.com/reset-password");
    	        return response;
    	    }

    	    // 2. Match password using BCrypt
    	    if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
    	        response.setMessage("Invalid password.");
    	        response.setSuccess(false);
    	        response.setResetPasswordLink("https://yourdomain.com/reset-password?user=" + user.getUserName());
    	        return response;
    	    }

    	    // 3. Success response
    	    response.setMessage("Login successful");
    	    response.setSuccess(true);
    	    response.setUserName(user.getUserName());
    	    response.setToken("mock-token-" + user.getUserName());
    	    response.setResetPasswordLink(null);

    	    return response;
    	}

}