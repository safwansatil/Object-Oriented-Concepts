package com.foodapp.exception;

/**
 * Thrown when a business rule is violated (422).
 */
public class BusinessRuleException extends AppException {
    public BusinessRuleException(String message) {
        super(message, 422);
    }
}
