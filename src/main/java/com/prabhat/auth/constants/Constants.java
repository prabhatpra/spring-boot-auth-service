package com.prabhat.auth.constants;

public class Constants {

    // Admin email (if using only one — optional if using AppProperties for list)
    public static final String ADMIN_EMAIL = "prabhatprajapati01@gmail.com";

    // Roles
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";

   
    // Reset password base URL (only if you're not using AppProperties)
    public static final String RESET_PASSWORD_BASE_URL = "https://yourdomain.com/reset-password";

    private Constants() {
        // Prevent instantiation
    }
    
    // Controller constant 
    public static final String BASE_API = "/user";
    public static final String REGISTER_API = "/register";
    public static final String LOGIN_API = "/login";
    
    
    
}
