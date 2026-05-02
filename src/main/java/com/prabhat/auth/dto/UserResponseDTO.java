package com.prabhat.auth.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
	
    private boolean success;
    private String message;
    private Long userId;
    private String userName;
    private String email;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime timestamp;

}

