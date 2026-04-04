package com.foodapp.model;

/**
 * User domain entity.
 */
public class User {
    private String id;
    private String name;
    private String email;
    private String passwordHash;
    private String phone;
    private UserRole role;
    private String createdAt;
    private boolean isActive;
    private String token; // Transient token for frontend

    public User() {}

    public User(String id, String name, String email, String passwordHash, String phone, UserRole role, String createdAt, boolean isActive) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.phone = phone;
        this.role = role;
        this.createdAt = createdAt;
        this.isActive = isActive;
        this.token = id; // Default token to ID for simplicity
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", isActive=" + isActive +
                '}';
    }
}
