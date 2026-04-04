package com.foodapp.util;

import java.util.UUID;

/**
 * Utility for generating UUID strings.
 */
public final class UUIDGenerator {
    private UUIDGenerator() {}

    public static String generate() {
        return UUID.randomUUID().toString();
    }
}
