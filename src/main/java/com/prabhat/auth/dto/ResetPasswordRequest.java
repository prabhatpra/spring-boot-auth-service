package com.prabhat.auth.dto;

import lombok.Data;

@Data
public class ResetPasswordRequest {

	private String token;
    private String newPassword;
    private String confirmPassword;
}
