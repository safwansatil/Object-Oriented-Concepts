package com.foodapp.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Validates user input.
 */
public final class Validator {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    private Validator() {}

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isNotBlank(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public static boolean isPositive(double value) {
        return value > 0;
    }

    public static List<String> validateRequired(Map<String, String> fields) {
        List<String> errors = new ArrayList<>();
        fields.forEach((name, value) -> {
            if (!isNotBlank(value)) {
                errors.add(name + " is required.");
            }
        });
        return errors;
    }
}
