package com.foodapp.exception;

import java.util.Collections;
import java.util.List;

/**
 * Thrown when validation fails (400).
 */
public class ValidationException extends AppException {
    private final List<String> errors;

    public ValidationException(String message) {
        this(message, Collections.singletonList(message));
    }

    public ValidationException(String message, List<String> errors) {
        super(message, 400);
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
