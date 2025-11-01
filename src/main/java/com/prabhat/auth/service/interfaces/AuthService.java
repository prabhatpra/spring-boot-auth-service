package com.prabhat.auth.service.interfaces;

import com.prabhat.auth.dto.LoginRequest;
import com.prabhat.auth.dto.LoginResponse;
import com.prabhat.auth.dto.SignupRequest;
import com.prabhat.auth.dto.SignupResponse;

public interface AuthService {

	 SignupResponse signup(SignupRequest request);
	
	 LoginResponse login(LoginRequest request);
}
