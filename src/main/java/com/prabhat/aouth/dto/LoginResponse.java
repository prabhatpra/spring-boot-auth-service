package com.prabhat.aouth.dto;

import lombok.Data;

@Data

public class LoginResponse {

	private String message;
	private boolean success;
	private String resetPasswordLink;
    private String token;             
    private Long userId;              
    private String userName; 
    private String role;

}
