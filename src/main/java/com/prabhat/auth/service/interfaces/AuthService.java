package com.prabhat.auth.service.interfaces;

import com.prabhat.auth.dto.LoginRequestDTO;
import com.prabhat.auth.dto.AuthResponseDTO;
import com.prabhat.auth.dto.RegisterRequestDTO;
import com.prabhat.auth.dto.UserResponseDTO;

public interface AuthService {

	 UserResponseDTO signup(RegisterRequestDTO request);
	
	 AuthResponseDTO login(LoginRequestDTO request);
}
