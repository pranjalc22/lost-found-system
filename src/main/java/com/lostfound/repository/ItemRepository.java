package com.lostfound.repository;

import com.lostfound.database.DBConnection;
import com.lostfound.model.Item;
import com.lostfound.model.LostItem;
import com.lostfound.model.FoundItem;
import com.lostfound.util.FileLogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Unit 5 - ArrayList, Unit 6 - JDBC
public class ItemRepository {

    // Save a new item to database
    public boolean reportItem(Item item) {
        String sql = "INSERT INTO items (item_name, category, description, location, date_reported, status, user_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, item.getItemName());
            stmt.setString(2, item.getCategory());
            stmt.setString(3, item.getDescription());
            stmt.setString(4, item.getLocation());
            stmt.setString(5, item.getDateReported());
            stmt.setString(6, item.getStatus());
            stmt.setInt(7, item.getUserId());
            stmt.executeUpdate();
            FileLogger.log("Item reported: " + item.getItemName() + " Status: " + item.getStatus());
            return true;
        } catch (SQLException e) {
            FileLogger.log("Report item failed: " + e.getMessage());
            return false;
        }
    }

    // Unit 5 - ArrayList to store items
    // Get all lost items
    public List<Item> getAllLostItems() {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items WHERE status = 'lost'";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            // Unit 5 - Iterator
            while (rs.next()) {
                LostItem item = new LostItem(
                    rs.getInt("item_id"),
                    rs.getString("item_name"),
                    rs.getString("category"),
                    rs.getString("description"),
                    rs.getString("location"),
                    rs.getString("date_reported"),
                    rs.getInt("user_id"),
                    ""
                );
                items.add(item);
            }
        } catch (SQLException e) {
            FileLogger.log("Get lost items failed: " + e.getMessage());
        }
        return items;
    }

    // Get all found items
    public List<Item> getAllFoundItems() {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items WHERE status = 'found'";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                FoundItem item = new FoundItem(
                    rs.getInt("item_id"),
                    rs.getString("item_name"),
                    rs.getString("category"),
                    rs.getString("description"),
                    rs.getString("location"),
                    rs.getString("date_reported"),
                    rs.getInt("user_id"),
                    rs.getString("location")
                );
                items.add(item);
            }
        } catch (SQLException e) {
            FileLogger.log("Get found items failed: " + e.getMessage());
        }
        return items;
    }
}