package com.foodapp.exception;

/**
 * Thrown when a resource is not found (404).
 */
public class ResourceNotFoundException extends AppException {
    public ResourceNotFoundException(String message) {
        super(message, 404);
    }
}
