package com.prabhat.aouth.exception;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(AuthException.UserAlreadyExistsException.class)
	public ResponseEntity<String> handleUserAlreadyExistsException(AuthException.UserAlreadyExistsException ex) {
	    log.warn("User already exists: {}", ex.getMessage());
	    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(AuthException.PasswordMismatchException.class)
	public ResponseEntity<String> handlePasswordMismatchException(AuthException.PasswordMismatchException ex) {
	    log.warn("Password mismatch: {}", ex.getMessage());
	    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(AuthException.UserNotFoundException.class)
	public ResponseEntity<String> handleUserNotFoundException(AuthException.UserNotFoundException ex) {
	    log.warn("User not found: {}", ex.getMessage());
	    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleValidationErrors(MethodArgumentNotValidException ex) {
	    String errors = ex.getBindingResult().getFieldErrors()
	            .stream()
	            .map(error -> error.getField() + ": " + error.getDefaultMessage())
	            .collect(Collectors.joining(", "));
	    log.warn("Validation failed: {}", errors);
	    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}


	@ExceptionHandler(AuthException.InvalidPasswordException.class)
	public ResponseEntity<String> handleInvalidPasswordException(AuthException.InvalidPasswordException ex) {
	    log.warn("Invalid password: {}", ex.getMessage());
	    return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
	}

	// Generic Exception handler for unhandled exceptions
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGenericException(Exception ex) {
	    log.error("Unhandled exception: ", ex);  // exception stack trace bhi log karega
	    return new ResponseEntity<>("Internal server error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}