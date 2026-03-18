package com.courtsync.se.security;

/**
 * Input validation utilities for user-facing data.
 */
public class InputValidator {

    /**
     * Validate email format (basic check).
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) return false;
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    /**
     * Validate password strength (min 6 chars, at least 1 digit).
     */
    public static boolean isValidPassword(String password) {
        if (password == null || password.length() < 6) return false;
        return password.matches(".*[0-9].*");
    }

    /**
     * Validate phone (optional, if provided must be non-empty and reasonable length).
     */
    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.isEmpty()) return true;
        return phone.matches("[0-9\\-\\+\\s()]{7,20}");
    }

    /**
     * Sanitize string by removing/escaping dangerous characters for CSV.
     */
    public static String sanitizeForCSV(String input) {
        if (input == null) return "";
        return input.replaceAll("[,\n\r\"']", " ");
    }
}
