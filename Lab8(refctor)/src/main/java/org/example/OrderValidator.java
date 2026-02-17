package org.example;

public interface OrderValidator {
    void validate(Order order) throws ValidationException;
}
