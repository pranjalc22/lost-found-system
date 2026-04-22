package com.lostfound.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.lostfound.database.DBConnection;
import com.lostfound.model.User;
import com.lostfound.util.FileLogger;

public class UserRepository {

    // Saves a new user to the database
    public boolean registerUser(User user) {
        String sql = "INSERT INTO users (name, email, phone, password) VALUES (?, ?, ?, ?)";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPhone());
            stmt.setString(4, user.getPassword());
            stmt.executeUpdate();//inserts user
            FileLogger.log("New user registered: " + user.getEmail());
            return true;//logs registration
        } catch (SQLException e) {
            FileLogger.log("Registration failed: " + e.getMessage());
            return false;
        }
    }

    public User loginUser(String email, String password) {
        //validating credentials:
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getInt("user_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("password")
                );
            }
        } catch (SQLException e) {
            FileLogger.log("Login failed: " + e.getMessage());
        }
        return null;//if login fails
    }

    public User getUserById(int userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getInt("user_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("password")
                );
            }
        } catch (SQLException e) {
            FileLogger.log("Get user by ID failed: " + e.getMessage());
        }
        return null;
    }

    public boolean updateUser(int userId, String name, String phone) {
        String sql = "UPDATE users SET name = ?, phone = ? WHERE user_id = ?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, phone);
            stmt.setInt(3, userId);
            stmt.executeUpdate();
            FileLogger.log("User updated: " + userId);
            return true;
        } catch (SQLException e) {
            FileLogger.log("Update user failed: " + e.getMessage());
            return false;
        }
    }
}
