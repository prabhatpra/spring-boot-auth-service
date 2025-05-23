package com.prabhat.aouth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignupResponse {

	private String userName;
	private String email;
	private String password;
	
	@Override
	public String toString() {
		return "SignupResponse{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' + 
                ", password='" + password + '\'' +
                '}';
	}
}
