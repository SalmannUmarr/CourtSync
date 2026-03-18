package com.courtsync.se.dao;

import com.courtsync.se.model.User;
import java.util.List;

/**
 * DAO interface for User CRUD operations.
 */
public interface UserDAO {
    User create(User user) throws Exception;
    User findById(int id);
    User findByEmail(String email);
    List<User> findAll();
    User update(User user) throws Exception;
    boolean delete(int id) throws Exception;
}
