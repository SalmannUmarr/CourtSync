package com.courtsync.se.model;

import com.courtsync.se.security.PasswordHasher;

/**
 * Abstract representation of a User in CourtSync SE.
 * Uses PasswordHasher for credential verification - plain-text passwords never stored.
 */
public abstract class User {
    protected int userId;
    protected String name;
    protected String email;
    protected String passwordHash;  // Only hashed version stored; never plain-text

    public User() {}

    public User(int userId, String name, String email, String passwordHash) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    /**
     * Attempts login by verifying provided credentials against stored hash.
     * Plain-text password is never logged or persisted.
     */
    public boolean login(String email, String plainPassword) {
        if (this.email == null || !this.email.equalsIgnoreCase(email)) return false;
        if (this.passwordHash == null || plainPassword == null) return false;
        try {
            return PasswordHasher.verifyPassword(plainPassword, this.passwordHash);
        } catch (Exception e) {
            return false;
        }
    }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
}
