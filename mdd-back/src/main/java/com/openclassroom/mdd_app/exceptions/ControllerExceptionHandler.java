package com.openclassroom.mdd_app.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.ExpiredJwtException;

@Order(2)
@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(CustomException.class) 
     public ResponseEntity<Object> handleCustomException(CustomException ex) {
	        var response = new HashMap<String, Object>();
	        response.put("message", ex.getMessage());
	        response.put("status", ex.getHttpStatus().value());
	        return ResponseEntity
	                .status(ex.getHttpStatus()) 
	                .body(response);
	}

	@ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Map<String, Object>> handleExpiredJwtException(ExpiredJwtException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Token expiré");
        response.put("status", HttpStatus.UNAUTHORIZED.value());
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}
