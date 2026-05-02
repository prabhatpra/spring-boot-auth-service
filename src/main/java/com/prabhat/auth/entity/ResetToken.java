package com.prabhat.auth.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ResetToken {

	private String token;
	private User user;
	private LocalDateTime expiryDate;
}
