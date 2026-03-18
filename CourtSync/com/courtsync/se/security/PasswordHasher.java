package com.courtsync.se.security;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Password hashing utility using SHA-256 with salt.
 * Passwords are stored as: salt$hash (Base64 encoded).
 * Plain-text passwords must never be written to logs.
 */
public class PasswordHasher {
    private static final int SALT_LENGTH = 16;
    private static final int ITERATIONS = 10000;

    /**
     * Hash a plaintext password with a random salt.
     * Returns: base64(salt)$base64(hash)
     */
    public static String hashPassword(String plaintext) throws Exception {
        if (plaintext == null || plaintext.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        byte[] salt = new byte[SALT_LENGTH];
        new SecureRandom().nextBytes(salt);
        byte[] hash = hashWithSalt(plaintext, salt);
        String saltB64 = Base64.getEncoder().encodeToString(salt);
        String hashB64 = Base64.getEncoder().encodeToString(hash);
        return saltB64 + "$" + hashB64;
    }

    /**
     * Verify a plaintext password against a stored hash.
     */
    public static boolean verifyPassword(String plaintext, String storedHash) throws Exception {
        if (plaintext == null || storedHash == null) return false;
        String[] parts = storedHash.split("\\$");
        if (parts.length != 2) return false;
        byte[] salt = Base64.getDecoder().decode(parts[0]);
        byte[] storedHashBytes = Base64.getDecoder().decode(parts[1]);
        byte[] computedHash = hashWithSalt(plaintext, salt);
        return constantTimeEquals(computedHash, storedHashBytes);
    }

    private static byte[] hashWithSalt(String password, byte[] salt) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt);
        byte[] hash = md.digest(password.getBytes("UTF-8"));
        for (int i = 1; i < ITERATIONS; i++) {
            md.reset();
            md.update(hash);
            hash = md.digest();
        }
        return hash;
    }

    /**
     * Constant-time byte array comparison to prevent timing attacks.
     */
    private static boolean constantTimeEquals(byte[] a, byte[] b) {
        if (a.length != b.length) return false;
        int result = 0;
        for (int i = 0; i < a.length; i++) {
            result |= a[i] ^ b[i];
        }
        return result == 0;
    }
}
