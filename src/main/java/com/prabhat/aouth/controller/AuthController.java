package com.prabhat.aouth.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prabhat.aouth.dto.LoginDTO;
import com.prabhat.aouth.dto.LoginResponse;
import com.prabhat.aouth.dto.SignupDTO;
import com.prabhat.aouth.dto.SignupResponse;
import com.prabhat.aouth.pojo.LoginRequest;
import com.prabhat.aouth.pojo.SignupRequest;
import com.prabhat.aouth.service.interfaces.AuthService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@Slf4j
public class AuthController {

	 @Autowired
	    private ModelMapper modelMapper;
	    private AuthService authService;
	    
	    public AuthController(ModelMapper modelMapper, AuthService authService) {
	    	this.modelMapper = modelMapper;
	    	this.authService = authService;
	    }
	 
	@PostMapping("/register")
	public ResponseEntity<SignupResponse> signup(@Valid @RequestBody SignupRequest signupReqeust){
		log.info("Signup request received: {}", signupReqeust);
		
		SignupDTO dto = modelMapper.map(signupReqeust, SignupDTO.class);
		log.debug("Mapped SignupDTO: {}", dto);
		
		SignupResponse response = authService.signup(dto);
		log.info("SignupResponse created: {}", response);	
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	  public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest){
		log.info("Login request received: {}", loginRequest);
		
		LoginDTO dto = modelMapper.map(loginRequest, LoginDTO.class);
		
		LoginResponse response = authService.login(dto);
		log.info("LoginResponse created:{}", response);
		
		return new ResponseEntity<>(response, HttpStatus.OK);

	}
	
}
