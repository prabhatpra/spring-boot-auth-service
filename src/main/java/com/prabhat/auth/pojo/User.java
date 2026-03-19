package com.prabhat.auth.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

	private int id;
	private String userName;
	private String email;
	private String password;
	private String role;
	
}
