package com.foodapp.service;

import com.foodapp.dao.UserDAO;
import com.foodapp.exception.AuthException;
import com.foodapp.exception.DatabaseException;
import com.foodapp.exception.ValidationException;
import com.foodapp.model.User;
import com.foodapp.model.UserRole;
import com.foodapp.util.PasswordHasher;
import com.foodapp.util.TimeUtil;
import com.foodapp.util.UUIDGenerator;
import com.foodapp.util.Validator;
import java.util.Optional;

/**
 * Service for user management and authentication.
 */
public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Registers a new user.
     */
    public User register(String name, String email, String plainPassword, String phone, UserRole role) {
        if (!Validator.isValidEmail(email)) {
            throw new ValidationException("Invalid email format.");
        }
        
        try {
            if (userDAO.findByEmail(email).isPresent()) {
                throw new ValidationException("Email already exists.");
            }

            String id = UUIDGenerator.generate();
            String passwordHash = PasswordHasher.hashPassword(plainPassword);
            String createdAt = TimeUtil.nowISO();
            
            User user = new User(id, name, email, passwordHash, phone, role, createdAt, true);
            userDAO.save(user);
            return user;
        } catch (DatabaseException e) {
            throw new RuntimeException("Service error during registration", e);
        }
    }

    /**
     * Authenticates a user.
     */
    public Optional<User> login(String email, String plainPassword) {
        try {
            Optional<User> userOpt = userDAO.findByEmail(email);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                if (PasswordHasher.verifyPassword(plainPassword, user.getPasswordHash())) {
                    if (!user.isActive()) {
                        throw new AuthException("User account is inactive.");
                    }
                    return Optional.of(user);
                }
            }
            return Optional.empty();
        } catch (DatabaseException e) {
            throw new RuntimeException("Service error during login", e);
        }
    }

    /**
     * Changes user password.
     */
    public void changePassword(String userId, String oldPassword, String newPassword) {
        try {
            Optional<User> userOpt = userDAO.findById(userId);
            if (userOpt.isEmpty()) {
                throw new ValidationException("User not found.");
            }
            
            User user = userOpt.get();
            if (!PasswordHasher.verifyPassword(oldPassword, user.getPasswordHash())) {
                throw new AuthException("Incorrect old password.");
            }
            
            String newHash = PasswordHasher.hashPassword(newPassword);
            userDAO.updatePassword(userId, newHash);
        } catch (DatabaseException e) {
            throw new RuntimeException("Service error during password change", e);
        }
    }
}
