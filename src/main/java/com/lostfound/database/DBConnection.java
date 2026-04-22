package com.lostfound.database;

import java.sql.Connection; //db connection
import java.sql.DriverManager;//helps to connect
import java.sql.SQLException;//handles db errors

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/lostfound_db";
    private static final String USER = "root";
    private static final String PASSWORD = "Pranjalsql123#"; // credentials

    private static Connection connection = null;

    private DBConnection() {}

    public static Connection getConnection() {
        try {//checks if no connection or connection closed
            if (connection == null || connection.isClosed()) {// if yes makes new connection
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Database connected successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
        return connection;
    }

    public static void closeConnection() {
        try {// esures safe closing
            if (connection != null && !connection.isClosed()) {
                connection.close();//closes db connection
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }
}