package com.courtsync.se.datastore;

import com.courtsync.se.dao.FileHandler;
import com.courtsync.se.model.Administrator;
import com.courtsync.se.model.Customer;
import com.courtsync.se.model.User;
import com.courtsync.se.security.PasswordHasher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * In-memory DataStore singleton. Loads users from data/users.csv.
 * No logging of passwords or sensitive data.
 */
public class DataStore {
    private static DataStore instance;
    private final List<User> users = new ArrayList<>();
    private final FileHandler fh = FileHandler.getInstance();

    private DataStore() {
        loadUsers();
        bootstrapDefaultAdminIfEmpty();
    }

    /** If no users exist, create default admin (admin@courtsync.se / admin123) for testing. */
    private void bootstrapDefaultAdminIfEmpty() {
        if (!users.isEmpty()) return;
        try {
            String hash = PasswordHasher.hashPassword("admin123");
            Administrator admin = new Administrator(1, "Admin User", "admin@courtsync.se", hash);
            users.add(admin);
            fh.append("users.csv", "1,ADMIN,Admin User,admin@courtsync.se," + hash);
        } catch (Exception ignored) { /* skip if bootstrap fails */ }
    }

    public static synchronized DataStore getInstance() {
        if (instance == null) instance = new DataStore();
        return instance;
    }

    private void loadUsers() {
        try {
            List<String> lines = fh.readAll("users.csv");
            for (String l : lines) {
                if (l == null || l.trim().isEmpty()) continue;
                try {
                    String[] p = l.split(",", -1);
                    if (p.length < 5) continue;
                    int id = Integer.parseInt(p[0].trim());
                    String role = p[1].trim();
                    String name = p[2].trim();
                    String email = p[3].trim();
                    String pwdHash = p[4].trim();
                    if (role.equalsIgnoreCase("CUSTOMER")) {
                        String phone = p.length > 5 ? p[5].trim() : "";
                        users.add(new Customer(id, name, email, pwdHash, phone));
                    } else {
                        users.add(new Administrator(id, name, email, pwdHash));
                    }
                } catch (Exception ignored) { /* skip invalid lines */ }
            }
        } catch (IOException ignored) { /* start with empty users */ }
    }

    public List<User> getUsers() { return users; }

    public int nextUserId() {
        int max = 0;
        for (User u : users) if (u.getUserId() > max) max = u.getUserId();
        return max + 1;
    }
}
