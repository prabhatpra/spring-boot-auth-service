package com.prabhat.aouth.dto;

import lombok.Data;

@Data
public class SignupDTO {

	private String userName;
	private String email;
	private String Password;
	private String confirmPassword;
	
	// Optionally, override toString() for debugging
	
	@Override
	public String toString() {
		return "SignupDTO{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                '}';
	}
}
