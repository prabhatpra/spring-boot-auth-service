package com.prabhat.auth.service.interfaces;

import com.prabhat.auth.dto.ForgotUsernameRequest;
import com.prabhat.auth.dto.ForgotUsernameResponse;

public interface AuthRecoveryService {
	
	ForgotUsernameResponse  sendUsernameToEmail(ForgotUsernameRequest request);

}
