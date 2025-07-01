package com.naukari.server.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.naukari.server.model.enums.ResponseStatus;
import com.naukari.server.utils.CustomResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Single handler for MethodArgumentNotValidException using CustomResponse
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomResponse<Map<String, String>>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        CustomResponse<Map<String, String>> response = new CustomResponse<>(
                ResponseStatus.VALIDATION_FAILED,
                "Validation failed",
                errors
        );

        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CustomResponse<Object>> handleConstraintViolation(ConstraintViolationException ex) {
        CustomResponse<Object> response = new CustomResponse<>(
                ResponseStatus.VALIDATION_FAILED,
                ex.getMessage(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<CustomResponse<Object>> handleBadCredentials(BadCredentialsException ex) {
        CustomResponse<Object> response = new CustomResponse<>(
                ResponseStatus.UNAUTHORIZED,
                "Invalid email or password",
                null
        );
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<CustomResponse<Object>> handleAuthenticationException(AuthenticationException ex) {
        CustomResponse<Object> response = new CustomResponse<>(
                ResponseStatus.UNAUTHORIZED,
                "Authentication failed: " + ex.getMessage(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CustomResponse<Object>> handleAccessDenied(AccessDeniedException ex) {
        CustomResponse<Object> response = new CustomResponse<>(
                ResponseStatus.FORBIDDEN,
                "Access denied: You don't have permission to access this resource",
                null
        );
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CustomResponse<Object>> handleRuntimeException(RuntimeException ex) {
        CustomResponse<Object> response = new CustomResponse<>(
                ResponseStatus.INTERNAL_ERROR,
                "An error occurred: " + ex.getMessage(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Single handler for generic Exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomResponse<Object>> handleGenericException(Exception ex) {
        CustomResponse<Object> response = new CustomResponse<>(
                ResponseStatus.INTERNAL_ERROR,
                "An unexpected error occurred",
                null
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}