package com.prabhat.auth.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
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
    public ResponseEntity<Map<String, Object>> handleUserAlreadyExistsException(AuthException.UserAlreadyExistsException ex) {
        log.warn("User already exists: {}", ex.getMessage());
        
        Map<String, Object> response = new HashMap<>();
        response.put("status", ex.getCode());
        response.put("error", "User Already Exists");
        response.put("message", ex.getMessage());
        response.put("timestamp", LocalDateTime.now());
        
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getCode()));
    }

    @ExceptionHandler(AuthException.PasswordMismatchException.class)
    public ResponseEntity<Map<String, Object>> handlePasswordMismatchException(AuthException.PasswordMismatchException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Password Mismatch");
        response.put("message", ex.getMessage());
        response.put("timestamp", LocalDateTime.now());
        log.warn("Password mismatch: {}", ex.getMessage());
        
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthException.UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(AuthException.UserNotFoundException ex) {
        log.warn("User not found: {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthException.InvalidPasswordException.class)
    public ResponseEntity<String> handleInvalidPasswordException(AuthException.InvalidPasswordException ex) {
        log.warn("Invalid password: {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
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

    // Generic Exception handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        log.error("Unhandled exception: ", ex);
        return new ResponseEntity<>("Internal server error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
