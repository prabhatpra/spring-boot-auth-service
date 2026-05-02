package com.prabhat.auth.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prabhat.auth.constants.Constants;
import com.prabhat.auth.constants.Messages;
import com.prabhat.auth.dto.AuthResponseDTO;
import com.prabhat.auth.dto.LoginRequestDTO;
import com.prabhat.auth.dto.RegisterRequestDTO;
import com.prabhat.auth.dto.UserResponseDTO;
import com.prabhat.auth.entity.User;
import com.prabhat.auth.exception.AuthException;
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
    @Transactional
    public UserResponseDTO signup(RegisterRequestDTO request) {
        log.info("Processing signup for email: {}", request.getEmail());

        // Password mismatch check
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            log.warn(Messages.PASSWORD_MISMATCH, request.getUserName());
            throw new AuthException.PasswordMismatchException(Messages.PASSWORD_MISMATCH);
        }

        // Email uniqueness check
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            log.warn(Messages.EMAIL_ALREADY_EXIST, request.getEmail());
            throw new AuthException.UserAlreadyExistsException(Messages.EMAIL_ALREADY_EXIST);
        }
        
        // UserName uniqueness check
        
        if(userRepository.findByUserName(request.getUserName()).isPresent()){
            log.warn(Messages.USERNAME_ALREADY_EXISTS, request.getUserName());	
            throw new AuthException.UserAlreadyExistsException(Messages.USERNAME_ALREADY_EXISTS);
        }

        // Password hashing
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        
        // Role assignment 
        String role = request.getEmail().equalsIgnoreCase(Constants.ADMIN_EMAIL) 
        		? Constants.ROLE_ADMIN : Constants.ROLE_USER;

        // Build user Object
        User user =  User.builder()
                  .userName(request.getUserName())
                  .email(request.getEmail())
                  .password(hashedPassword)
                  .role(role)
                  .build();

      
        // Save user in DB
        userRepository.save(user);

        log.info("User registered successfully: {}, role: {}", user.getEmail(), user.getRole());

        // Return signup response
        return UserResponseDTO.builder()
        		.success(true)
        		.message(Messages.SIGNUP_SUCCESS)
        		.userName(user.getUserName())
        		.email(user.getEmail())
                .build();
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO request) {

        User user = userRepository.findByUserName(request.getUserName())
        		.orElseThrow(() -> new AuthException.UserNotFoundException(Messages.LOGIN_USER_NOT_FOUND));
    	
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
        	return AuthResponseDTO.builder()
        			.success(false)
        			.message(Messages.LOGIN_INVALID_PASSWORD +
        					" Reset here: " + Constants.RESET_PASSWORD_BASE_URL)
        			.build();
        }


        String token = jwtTokenUtil.generateToken(user.getEmail(), user.getRole());

        return AuthResponseDTO.builder()
                .success(true)
                .message(Messages.LOGIN_SUCCESS)
                .token(token)
                .userId((long) user.getId())
                .userName(user.getUserName())
                .role(user.getRole())
                .build();
    }
}
