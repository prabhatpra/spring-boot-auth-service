package com.prabhat.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private boolean success;
    private String message;
    private String token;
    private Long userId;
    private String userName;
    private String role;
}
