package com.lostfound.repository;

import com.lostfound.database.DBConnection;
import com.lostfound.model.User;
import com.lostfound.util.FileLogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Unit 6 - JDBC, Unit 5 - Exception handling
public class UserRepository {

    // Unit 1 - Instance method
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
            stmt.executeUpdate();
            FileLogger.log("New user registered: " + user.getEmail());
            return true;
        } catch (SQLException e) {
            FileLogger.log("Registration failed: " + e.getMessage());
            return false;
        }
    }

    // Unit 6 - JDBC query to find user by email and password
    public User loginUser(String email, String password) {
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
        return null;
    }
}