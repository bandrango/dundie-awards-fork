package com.ninjaone.dundie_awards.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.ninjaone.dundie_awards.domain.exception.DomainException;
import com.ninjaone.dundie_awards.domain.exception.EmployeeNotFoundException;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Global Exception Handler
 * Centralized exception handling for REST API.
 * Provides consistent error response format across all endpoints.
 */
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, Object>> handleEmployeeNotFoundException(EmployeeNotFoundException ex) {
        logger.warn("Employee not found: {}", ex.getMessage());
        return createErrorResponse(HttpStatus.NOT_FOUND, "Not found", ex.getMessage());
    }

    @ExceptionHandler(DomainException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> handleDomainException(DomainException ex) {
        logger.warn("Domain validation error: {}", ex.getMessage());
        return createErrorResponse(HttpStatus.BAD_REQUEST, "Invalid request", ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.warn("Invalid argument in request: {}", ex.getMessage());
        return createErrorResponse(HttpStatus.BAD_REQUEST, "Invalid request", ex.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, Object>> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        logger.warn("Resource not found - Path: {} | Method: {}", ex.getRequestURL(), ex.getHttpMethod());
        return createErrorResponse(HttpStatus.NOT_FOUND, "Not found", "The requested resource was not found");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Map<String, Object>> handleGlobalException(Exception ex) {
        logger.error("Unhandled exception: {} | Type: {}", ex.getMessage(), ex.getClass().getName(), ex);
        return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error",
                "An unexpected error occurred. Please check the logs for details.");
    }

    /**
     * Create standardized error response.
     * Reduces code duplication across exception handlers.
     */
    private ResponseEntity<Map<String, Object>> createErrorResponse(HttpStatus status, String error, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", status.value());
        response.put("error", error);
        response.put("message", message);
        return ResponseEntity.status(status).body(response);
    }
}
