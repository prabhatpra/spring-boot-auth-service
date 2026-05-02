package com.prabhat.auth.recoverydto;

import lombok.Data;

@Data
public class ResetPasswordRequest {

	private String token;
	private String newPassword;
	private String confirmPassword;
}
