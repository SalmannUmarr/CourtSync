package com.courtsync.se.dao.impl;

import com.courtsync.se.dao.FileHandler;
import com.courtsync.se.dao.UserDAO;
import com.courtsync.se.datastore.DataStore;
import com.courtsync.se.model.Administrator;
import com.courtsync.se.model.Customer;
import com.courtsync.se.model.User;
import com.courtsync.se.security.InputValidator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * UserDAO implementation. Stores only hashed passwords in CSV - never plain-text.
 */
public class UserDAOImpl implements UserDAO {
    private static final String USERS_FILE = "users.csv";
    private final DataStore ds = DataStore.getInstance();
    private final FileHandler fh = FileHandler.getInstance();

    @Override
    public User create(User user) throws Exception {
        int id = ds.nextUserId();
        user.setUserId(id);
        ds.getUsers().add(user);
        String csv = userToCSV(user);
        try {
            fh.append(USERS_FILE, csv);
        } catch (IOException e) {
            throw new Exception("Failed to persist user", e);
        }
        return user;
    }

    private String escape(String s) { return s == null ? "" : s.replace(",", " "); }

    @Override
    public User findById(int id) {
        for (User u : ds.getUsers()) if (u.getUserId() == id) return u;
        return null;
    }

    @Override
    public User findByEmail(String email) {
        if (email == null) return null;
        for (User u : ds.getUsers()) {
            if (u.getEmail() != null && u.getEmail().equalsIgnoreCase(email)) return u;
        }
        return null;
    }

    @Override
    public List<User> findAll() { return new ArrayList<>(ds.getUsers()); }

    @Override
    public User update(User user) throws Exception {
        User found = findById(user.getUserId());
        if (found == null) throw new Exception("User not found");
        ds.getUsers().remove(found);
        ds.getUsers().add(user);
        List<String> lines = new ArrayList<>();
        for (User u : ds.getUsers()) lines.add(userToCSV(u));
        try { fh.writeAll(USERS_FILE, lines); } catch (IOException e) { throw new Exception(e); }
        return user;
    }

    @Override
    public boolean delete(int id) throws Exception {
        User u = findById(id);
        if (u == null) return false;
        ds.getUsers().remove(u);
        List<String> lines = new ArrayList<>();
        for (User usr : ds.getUsers()) lines.add(userToCSV(usr));
        try { fh.writeAll(USERS_FILE, lines); } catch (IOException e) { throw new Exception(e); }
        return true;
    }

    /**
     * Writes only hashed password to CSV - plain-text never persisted.
     */
    private String userToCSV(User u) {
        if (u instanceof Customer) {
            Customer c = (Customer) u;
            return c.getUserId() + ",CUSTOMER," + escape(c.getName()) + ","
                    + InputValidator.sanitizeForCSV(c.getEmail()) + ","
                    + InputValidator.sanitizeForCSV(c.getPasswordHash()) + ","
                    + InputValidator.sanitizeForCSV(c.getPhone());
        } else {
            Administrator a = (Administrator) u;
            return a.getUserId() + ",ADMIN," + escape(a.getName()) + ","
                    + InputValidator.sanitizeForCSV(a.getEmail()) + ","
                    + InputValidator.sanitizeForCSV(a.getPasswordHash());
        }
    }
}
