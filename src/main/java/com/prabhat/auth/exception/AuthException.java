package com.prabhat.auth.exception;

public class AuthException {

    // User already registered
    public static class UserAlreadyExistsException extends RuntimeException {
    	private final int code = 409; // HTTP Conflict
        public UserAlreadyExistsException(String message) {
            super(message);
        }
        public int getCode() { return code; }
    }

    // Password and confirm password do not match
    public static class PasswordMismatchException extends RuntimeException {
    	private final int code = 400; // HTTP Bad Request
        public PasswordMismatchException(String message) {
            super(message);
        }
        public int getCode() { return code; }
    }

    // User not found in login
    public static class UserNotFoundException extends RuntimeException {
    	private final int code = 404; // HTTP Not Found
        public UserNotFoundException(String message) {
            super(message);
        }
        public int getCode() {return code;}
    }

    // Password is invalid in login
    public static class InvalidPasswordException extends RuntimeException {
    	private final int code = 401; // HTTP Unauthorized
        public InvalidPasswordException(String message) {
            super(message);
        }
        public int getCode() {
        	return code;
        }
    }

    }
    
