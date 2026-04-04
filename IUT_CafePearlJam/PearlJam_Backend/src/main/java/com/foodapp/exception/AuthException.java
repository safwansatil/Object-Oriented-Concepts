package com.foodapp.exception;

/**
 * Thrown when authentication or authorization fails (401/403).
 */
public class AuthException extends AppException {
    public AuthException(String message) {
        super(message, 401);
    }
    
    public AuthException(String message, int statusCode) {
        super(message, statusCode);
    }
}
