package com.prabhat.auth.constants;

public class Messages {

	private Messages() {} 
	
	// AuthService Constant Value
    // Login/Signup messages (optional but cleaner)
    public static final String PASSWORD_MISMATCH = "Password and confirm password do not match";
    public static final String SIGNUP_SUCCESS = "User registered successfully";
    public static final String EMAIL_ALREADY_EXIST = "User with this email already exists";
    public static final String USERNAME_ALREADY_EXISTS = "User with this UserName already exists";
    
    public static final String LOGIN_SUCCESS = "Login successful";
    public static final String LOGIN_USER_NOT_FOUND = "User not found. Please register.";
    public static final String LOGIN_INVALID_PASSWORD = "Login Faild: Invalid password.";

}
