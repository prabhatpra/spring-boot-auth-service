package com.prabhat.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO {

	@NotBlank(message = "Username is required")
	private String userName;

	@NotBlank(message = "Password is required")
	private String password;

}
