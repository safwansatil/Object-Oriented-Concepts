package com.foodapp.exception;

/**
 * Thrown when a database error occurs.
 */
public class DatabaseException extends AppException {
    public DatabaseException(String message) {
        super(message, 500);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause, 500);
    }
}
