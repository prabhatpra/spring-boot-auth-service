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

    // Common method to build response
    private ResponseEntity<Map<String, Object>> buildResponse(int status, String error, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put("error", error);
        response.put("message", message);
        response.put("timestamp", LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.valueOf(status));
    }

    // User already exists
    @ExceptionHandler(AuthException.UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleUserAlreadyExistsException(AuthException.UserAlreadyExistsException ex) {
        log.warn("User already exists: {}", ex.getMessage());
        return buildResponse(ex.getCode(), "User Already Exists", ex.getMessage());
    }

    // Password mismatch
    @ExceptionHandler(AuthException.PasswordMismatchException.class)
    public ResponseEntity<Map<String, Object>> handlePasswordMismatchException(AuthException.PasswordMismatchException ex) {
        log.warn("Password mismatch: {}", ex.getMessage());
        return buildResponse(ex.getCode(), "Password Mismatch", ex.getMessage());
    }

    // User not found
    @ExceptionHandler(AuthException.UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFoundException(AuthException.UserNotFoundException ex) {
        log.warn("User not found: {}", ex.getMessage());
        return buildResponse(ex.getCode(), "User Not Found", ex.getMessage());
    }

    // Invalid password
    @ExceptionHandler(AuthException.InvalidPasswordException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidPasswordException(AuthException.InvalidPasswordException ex) {
        log.warn("Invalid password: {}", ex.getMessage());
        return buildResponse(ex.getCode(), "Invalid Password", ex.getMessage());
    }

    // Invalid token
    @ExceptionHandler(AuthException.InvalidTokenException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidTokenException(AuthException.InvalidTokenException ex) {
        log.warn("Invalid token: {}", ex.getMessage());
        return buildResponse(ex.getCode(), "Invalid Token", ex.getMessage());
    }

    // Validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {

        String errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        log.warn("Validation failed: {}", errors);

        return buildResponse(400, "Validation Error", errors);
    }

    // Generic exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        log.error("Unhandled exception: ", ex);

        return buildResponse(
                500,
                "Internal Server Error",
                "Something went wrong"
        );
    }
}