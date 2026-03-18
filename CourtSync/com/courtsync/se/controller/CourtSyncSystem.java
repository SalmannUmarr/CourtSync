package com.courtsync.se.controller;

import com.courtsync.se.dao.impl.UserDAOImpl;
import com.courtsync.se.model.Customer;
import com.courtsync.se.model.User;
import com.courtsync.se.security.PasswordHasher;

/**
 * Controller for registration and login. Uses PasswordHasher - plain-text
 * passwords never written to logs or storage.
 */
public class CourtSyncSystem {
    private final UserDAOImpl userDAO = new UserDAOImpl();

    /**
     * Validate credentials and return matching User or null.
     * Invalid credentials: returns null (caller shows "Invalid username or password").
     */
    public User login(String email, String password) {
        User u = userDAO.findByEmail(email);
        if (u != null && u.login(email, password)) return u;
        return null;
    }

    /**
     * Register a new customer. Validates data, checks duplicate email,
     * hashes password, and persists to CSV. Plain-text password never stored.
     */
    public Customer registerCustomer(String name, String email, String phone, String password) throws Exception {
        if (userDAO.findByEmail(email) != null) {
            throw new Exception("Email already registered");
        }
        String hashedPassword = PasswordHasher.hashPassword(password);
        Customer c = new Customer(0, name, email, hashedPassword, phone);
        return (Customer) userDAO.create(c);
    }
}
