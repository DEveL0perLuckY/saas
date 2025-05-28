package com.backend.app.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.ServletException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServletException.class)
    public ResponseEntity<String> handleUnauthorized(ServletException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Invalid or missing token");
    }
}