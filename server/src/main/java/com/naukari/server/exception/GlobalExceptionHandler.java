package com.naukari.server.exception;

import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import com.naukari.server.model.enums.ResponseStatus;
import com.naukari.server.utils.CustomResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

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

        logger.warn("Validation failed: {}", errors);

        CustomResponse<Map<String, String>> response = new CustomResponse<>(
                ResponseStatus.VALIDATION_FAILED,
                "Validation failed",
                errors
        );

        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CustomResponse<Object>> handleConstraintViolation(ConstraintViolationException ex) {
        logger.warn("Constraint violation: {}", ex.getMessage());

        CustomResponse<Object> response = new CustomResponse<>(
                ResponseStatus.VALIDATION_FAILED,
                ex.getMessage(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomExceptions.UserNotFoundException.class)
    public ResponseEntity<CustomResponse<Object>> handleUserNotFound(CustomExceptions.UserNotFoundException ex) {
        logger.warn("User not found: {}", ex.getMessage());

        CustomResponse<Object> response = new CustomResponse<>(
                ResponseStatus.NOT_FOUND,
                ex.getMessage(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomExceptions.UserAlreadyExistsException.class)
    public ResponseEntity<CustomResponse<Object>> handleUserAlreadyExists(CustomExceptions.UserAlreadyExistsException ex) {
        logger.warn("User already exists: {}", ex.getMessage());

        CustomResponse<Object> response = new CustomResponse<>(
                ResponseStatus.ALREADY_EXISTS,
                ex.getMessage(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CustomExceptions.EmailServiceException.class)
    public ResponseEntity<CustomResponse<Object>> handleEmailService(CustomExceptions.EmailServiceException ex) {
        logger.error("Email service error: {}", ex.getMessage(), ex);

        CustomResponse<Object> response = new CustomResponse<>(
                ResponseStatus.INTERNAL_ERROR,
                "Failed to send email: " + ex.getMessage(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomExceptions.InvalidTokenException.class)
    public ResponseEntity<CustomResponse<Object>> handleInvalidToken(CustomExceptions.InvalidTokenException ex) {
        logger.warn("Invalid token: {}", ex.getMessage());

        CustomResponse<Object> response = new CustomResponse<>(
                ResponseStatus.UNAUTHORIZED,
                ex.getMessage(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<CustomResponse<Object>> handleBadCredentials(BadCredentialsException ex) {
        logger.warn("Bad credentials attempt");

        CustomResponse<Object> response = new CustomResponse<>(
                ResponseStatus.UNAUTHORIZED,
                "Invalid email or password",
                null
        );
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<CustomResponse<Object>> handleAuthenticationException(AuthenticationException ex) {
        logger.warn("Authentication failed: {}", ex.getMessage());

        CustomResponse<Object> response = new CustomResponse<>(
                ResponseStatus.UNAUTHORIZED,
                "Authentication failed: " + ex.getMessage(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CustomResponse<Object>> handleAccessDenied(AccessDeniedException ex) {
        logger.warn("Access denied: {}", ex.getMessage());

        CustomResponse<Object> response = new CustomResponse<>(
                ResponseStatus.FORBIDDEN,
                "Access denied: You don't have permission to access this resource",
                null
        );
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    // Generic Exceptions
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CustomResponse<Object>> handleRuntimeException(RuntimeException ex) {
        logger.error("Runtime exception occurred: {}", ex.getMessage(), ex);

        CustomResponse<Object> response = new CustomResponse<>(
                ResponseStatus.INTERNAL_ERROR,
                "An error occurred: " + ex.getMessage(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomResponse<Object>> handleGenericException(Exception ex) {
        logger.error("Unexpected exception occurred: {}", ex.getMessage(), ex);

        CustomResponse<Object> response = new CustomResponse<>(
                ResponseStatus.INTERNAL_ERROR,
                "An unexpected error occurred",
                null
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}