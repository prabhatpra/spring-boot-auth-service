package com.prabhat.auth.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.prabhat.auth.constants.Constants;
import com.prabhat.auth.constants.Messages;
import com.prabhat.auth.dto.LoginRequest;
import com.prabhat.auth.dto.LoginResponse;
import com.prabhat.auth.dto.SignupRequest;
import com.prabhat.auth.dto.SignupResponse;
import com.prabhat.auth.exception.AuthException;
import com.prabhat.auth.pojo.User;
import com.prabhat.auth.repository.UserRepository;
import com.prabhat.auth.security.JwtTokenUtil;
import com.prabhat.auth.service.interfaces.AuthService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

	private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;
    
    
    @Autowired
    public AuthServiceImpl(PasswordEncoder passwordEncoder, 
                           UserRepository userRepository,
                           JwtTokenUtil jwtTokenUtil) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }


    @Override
    public SignupResponse signup(SignupRequest request) {
        log.info("Processing signup for email: {}", request.getEmail());

        // Password mismatch check
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            log.warn(Messages.PASSWORD_MISMATCH, request.getUserName());
            throw new AuthException.PasswordMismatchException(Messages.PASSWORD_MISMATCH);
        }

        // Email uniqueness check
        boolean emailExists = userRepository.findByEmail(request.getEmail()).isPresent();
        if (emailExists) {
            log.warn(Messages.EMAIL_ALREADY_EXIST, request.getEmail());
            throw new AuthException.UserAlreadyExistsException(Messages.EMAIL_ALREADY_EXIST);
        }
        
        // UserName uniqueness check
        boolean userExists = userRepository.findByUserName(request.getUserName()).isPresent();
        if(userExists){
            log.warn(Messages.USERNAME_ALREADY_EXISTS, request.getUserName());	
            throw new AuthException.UserAlreadyExistsException(Messages.USERNAME_ALREADY_EXISTS);
        }

        // Password hashing
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        
        // Role assignment 
        String role = request.getEmail().equalsIgnoreCase(Constants.ADMIN_EMAIL) 
        		? Constants.ROLE_ADMIN : Constants.ROLE_USER;

        // Build user Object
        User user = new User(); 
        user.setUserName(request.getUserName());
        user.setEmail(request.getEmail());
        user.setPassword(hashedPassword);
        user.setRole(role);

      
        // Save user in DB
        userRepository.saveUser(user);

        log.info("User registered successfully: {}, role: {}", user.getEmail(), user.getRole());

        // Return signup response
        return new SignupResponse(true, Messages.SIGNUP_SUCCESS   ,
        		user.getUserName(), 
        		user.getEmail());
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        log.info("Processing login for username: {}", request.getUserName());

        Optional<User> optionalUser = userRepository.findByUserName(request.getUserName());

        if (optionalUser.isEmpty()) {
            log.warn(Messages.LOGIN_USER_NOT_FOUND, request.getUserName());
            return new LoginResponse(false, Messages.LOGIN_USER_NOT_FOUND,
                    Constants.RESET_PASSWORD_BASE_URL, null, null, null);
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            log.warn(Messages.LOGIN_INVALID_PASSWORD, request.getUserName());
            return new LoginResponse(false, Messages.LOGIN_INVALID_PASSWORD,
                    Constants.RESET_PASSWORD_BASE_URL+"?user=" + user.getUserName(), null, null, null);
        }

        String role = user.getRole();
        String token = jwtTokenUtil.generateToken(user.getEmail(), role);
        log.info(Messages.LOGIN_SUCCESS, user.getUserName(), role);

        return new LoginResponse(true, Messages.LOGIN_SUCCESS,
                token,
                (long) user.getId(),
                user.getUserName(),
                role);
    }
}
