package com.courtsync.se.model;

/**
 * Administrator user type with management privileges.
 */
public class Administrator extends User {

    public Administrator() { super(); }

    public Administrator(int userId, String name, String email, String passwordHash) {
        super(userId, name, email, passwordHash);
    }
}
