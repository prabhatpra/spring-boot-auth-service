package com.prabhat.aouth.exception;

public class AuthException {

	public static class UserAlreadyExistsException extends RuntimeException{
		public UserAlreadyExistsException(String message) {
			super(message);
		}
	}
	
	public static class PasswordMismatchException extends RuntimeException{
		public PasswordMismatchException (String message) {
			super(message);
		}
	}
	
	public static class UserNotFoundException extends RuntimeException{
		public UserNotFoundException  (String message) {
			super(message);
		}
	}
	
	public static class handleValidationErrors extends RuntimeException{
		public handleValidationErrors(String message) {
			super(message);
		}
	}
	
	public static class InvalidPasswordException extends RuntimeException{
		public InvalidPasswordException(String message) {
			super(message);
		}
	}
}
