package com.prabhat.auth.service.interfaces;

public interface AuthRecoveryService {
	
	void  forgotPassword(String email);
	
	void resetPassword(String token, String newPassword);

}
