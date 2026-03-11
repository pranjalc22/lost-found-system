package com.lostfound.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Unit 6 - JDBC Database Connectivity
// Unit 1 - Static method, private constructor (Singleton pattern)
public class DBConnection {

    // Unit 1 - Static variables
    private static final String URL = "jdbc:mysql://localhost:3306/lostfound_db";
    private static final String USER = "root";
    private static final String PASSWORD = "Pranjalsql123#"; // Change this!

    private static Connection connection = null;

    // Unit 1 - Private constructor so nobody can create object of this class
    private DBConnection() {}

    // Unit 1 - Static method to get connection
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Database connected successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
        return connection;
    }

    // Unit 6 - Close connection
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }
}