package com.lostfound.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import com.lostfound.database.DBConnection;
import com.lostfound.model.FoundItem;
import com.lostfound.model.Item;
import com.lostfound.model.LostItem;
import com.lostfound.util.FileLogger;

public class ItemRepository {

    private class ResultSetItemIterator implements Iterator<Item> {
        private final ResultSet resultSet;
        private boolean hasNext;
        private boolean checked;

        ResultSetItemIterator(ResultSet resultSet) {
            this.resultSet = resultSet;
        }

        @Override
        public boolean hasNext() {
            if (!checked) {
                try {
                    hasNext = resultSet.next();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                checked = true;
            }
            return hasNext;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more rows in ResultSet.");
            }
            checked = false;
            try {
                return mapItem(resultSet);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Item mapItem(ResultSet rs) throws SQLException {
        String status = rs.getString("status");
        Item item;
        if ("found".equalsIgnoreCase(status)) {
            item = new FoundItem(
                rs.getInt("item_id"),
                rs.getString("item_name"),
                rs.getString("category"),
                rs.getString("description"),
                rs.getString("location"),
                rs.getString("date_reported"),
                rs.getInt("user_id"),
                rs.getString("location")
            );
        } else {
            item = new LostItem(
                rs.getInt("item_id"),
                rs.getString("item_name"),
                rs.getString("category"),
                rs.getString("description"),
                rs.getString("location"),
                rs.getString("date_reported"),
                rs.getInt("user_id"),
                ""
            );
        }
        item.setImagePath(rs.getString("image_path"));
        item.setRecovered(rs.getBoolean("is_recovered"));
        return item;
    }

    // Saves a new item to the database including image URL
    public boolean reportItem(Item item) {
        String sql = "INSERT INTO items (item_name, category, description, location, date_reported, status, user_id, image_path) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
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
            stmt.setString(8, item.getImagePath());
            stmt.executeUpdate();
            FileLogger.log("Item reported: " + item.getItemName() + " Status: " + item.getStatus());
            return true;
        } catch (SQLException e) {
            FileLogger.log("Report item failed: " + e.getMessage());
            return false;
        }
    }

    // Fetches all lost items from the database
    public List<Item> getAllLostItems() {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items WHERE status = 'lost' AND is_recovered = false";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            Iterator<Item> iterator = new ResultSetItemIterator(rs);
            while (iterator.hasNext()) {
                items.add(iterator.next());
            }
        } catch (RuntimeException e) {
            FileLogger.log("Get lost items failed: " + e.getMessage());
        } catch (SQLException e) {
            FileLogger.log("Get lost items failed: " + e.getMessage());
        }
        return items;
    }

    // Fetches all found items from the database
    public List<Item> getAllFoundItems() {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items WHERE status = 'found' AND is_recovered = false";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                items.add(mapItem(rs));
            }
        } catch (SQLException e) {
            FileLogger.log("Get found items failed: " + e.getMessage());
        }
        return items;
    }

    public List<Item> getAllRecoveredItems() {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items WHERE is_recovered = true";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                items.add(mapItem(rs));
            }
        } catch (SQLException e) {
            FileLogger.log("Get recovered items failed: " + e.getMessage());
        }
        return items;
    }

    public List<Item> getAllItemsByCategory(String status, String category) {
        List<Item> items = new ArrayList<>();
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt;
            if (category == null || category.trim().isEmpty() || "All Categories".equalsIgnoreCase(category)) {
                if ("lost".equalsIgnoreCase(status) || "found".equalsIgnoreCase(status)) {
                    stmt = conn.prepareStatement("SELECT * FROM items WHERE status = ? AND is_recovered = false");
                } else {
                    stmt = conn.prepareStatement("SELECT * FROM items WHERE status = ?");
                }
                stmt.setString(1, status);
            } else {
                if ("lost".equalsIgnoreCase(status) || "found".equalsIgnoreCase(status)) {
                    stmt = conn.prepareStatement("SELECT * FROM items WHERE status = ? AND category = ? AND is_recovered = false");
                } else {
                    stmt = conn.prepareStatement("SELECT * FROM items WHERE status = ? AND category = ?");
                }
                stmt.setString(1, status);
                stmt.setString(2, category);
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                items.add(mapItem(rs));
            }
        } catch (SQLException e) {
            FileLogger.log("Get items by category failed: " + e.getMessage());
        }
        return items;
    }

    public boolean saveClaim(int itemId, int claimerId, String message, String date) {
        String sql = "INSERT INTO claims (item_id, claimer_id, message, claim_date) VALUES (?, ?, ?, ?)";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, itemId);
            stmt.setInt(2, claimerId);
            stmt.setString(3, message);
            stmt.setString(4, date);
            stmt.executeUpdate();
            FileLogger.log("Claim saved for item ID: " + itemId + " by user ID: " + claimerId);
            return true;
        } catch (SQLException e) {
            FileLogger.log("Save claim failed: " + e.getMessage());
            return false;
        }
    }

    public boolean markItemAsRecovered(int itemId) {
        String sql = "UPDATE items SET is_recovered = true, status = 'found' WHERE item_id = ?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, itemId);
            stmt.executeUpdate();
            FileLogger.log("Item marked as recovered: " + itemId);
            return true;
        } catch (SQLException e) {
            FileLogger.log("Mark item as recovered failed: " + e.getMessage());
            return false;
        }
    }

    public boolean markItemAsLost(int itemId) {
        String sql = "UPDATE items SET is_recovered = false, status = 'lost' WHERE item_id = ?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, itemId);
            stmt.executeUpdate();
            FileLogger.log("Item marked as lost again: " + itemId);
            return true;
        } catch (SQLException e) {
            FileLogger.log("Mark item as lost failed: " + e.getMessage());
            return false;
        }
    }

    public List<Object[]> getClaimsByItemId(int itemId) {
        List<Object[]> claims = new ArrayList<>();
        String sql = "SELECT u.name, c.message, c.claim_date, c.status "
                   + "FROM claims c "
                   + "JOIN users u ON c.claimer_id = u.user_id "
                   + "WHERE c.item_id = ?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, itemId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                claims.add(new Object[] {
                    rs.getString("name"),
                    rs.getString("message"),
                    rs.getString("claim_date"),
                    rs.getString("status")
                });
            }
        } catch (SQLException e) {
            FileLogger.log("Get claims by item ID failed: " + e.getMessage());
        }
        return claims;
    }

    public List<Item> getItemsByUserId(int userId) {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items WHERE user_id = ?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                items.add(mapItem(rs));
            }
        } catch (SQLException e) {
            FileLogger.log("Get items by user ID failed: " + e.getMessage());
        }
        return items;
    }

    public int getClaimsCountByUserId(int userId) {
        String sql = "SELECT COUNT(*) FROM claims WHERE claimer_id = ?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            FileLogger.log("Get claims count by user ID failed: " + e.getMessage());
        }
        return 0;
    }
}
