package com.prabhat.aouth.service.interfaces;

import com.prabhat.aouth.dto.LoginDTO;
import com.prabhat.aouth.dto.LoginResponse;
import com.prabhat.aouth.dto.SignupDTO;
import com.prabhat.aouth.dto.SignupResponse;

public interface AuthService {

	public SignupResponse signup(SignupDTO signupDTO);
	
	public LoginResponse login(LoginDTO loginDTO);
}
