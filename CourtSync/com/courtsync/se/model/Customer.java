package com.courtsync.se.model;

/**
 * Customer user type with phone information.
 */
public class Customer extends User {
    private String phone;

    public Customer() { super(); }

    public Customer(int userId, String name, String email, String passwordHash, String phone) {
        super(userId, name, email, passwordHash);
        this.phone = phone;
    }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}
