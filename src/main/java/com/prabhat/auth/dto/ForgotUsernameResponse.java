package com.prabhat.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForgotUsernameResponse {
    
    private boolean success;
    private String message;
    private String username;

}

