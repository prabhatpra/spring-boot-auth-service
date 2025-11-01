package com.prabhat.auth.dto;

import lombok.Data;

@Data
public class ForgotUsernameRequest {
    private String emailOrUsername;
}
