package com.prabhat.aouth.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequest {

	private String userName;
	private String password;
	
}
