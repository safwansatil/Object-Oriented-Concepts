package com.foodapp.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles password hashing and verification using SHA-256 and a random salt.
 */
public final class PasswordHasher {
    private static final Logger LOGGER = Logger.getLogger(PasswordHasher.class.getName());
    private static final String ALGORITHM = "SHA-256";
    private static final SecureRandom RANDOM = new SecureRandom();

    private PasswordHasher() {}

    public static String hashPassword(String plain) {
        byte[] saltBytes = new byte[8];
        RANDOM.nextBytes(saltBytes);
        String salt = Base64.getEncoder().encodeToString(saltBytes);
        
        String hash = hexHash(salt + plain);
        return salt + ":" + hash;
    }

    public static boolean verifyPassword(String plain, String stored) {
        if (stored == null || !stored.contains(":")) {
            return false;
        }
        
        String[] parts = stored.split(":");
        String salt = parts[0];
        String storedHash = parts[1];
        
        String computedHash = hexHash(salt + plain);
        return computedHash.equals(storedHash);
    }

    private static String hexHash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
            byte[] encodedhash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            
            StringBuilder hexString = new StringBuilder(2 * encodedhash.length);
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.log(Level.SEVERE, "Algorithm not found", e);
            return "";
        }
    }
}
