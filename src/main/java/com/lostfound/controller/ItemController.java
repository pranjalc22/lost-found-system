package com.lostfound.controller;

import com.lostfound.model.Item;
import com.lostfound.service.ItemService;
import com.lostfound.util.CustomException;

import java.util.List;
import java.util.ArrayList;

// Connects GUI to service layer, handles exceptions
public class ItemController {

    private ItemService itemService;

    public ItemController() {
        this.itemService = new ItemService();
    }

    // Passes lost item data including image URL to service layer
    public boolean reportLostItem(String itemName, String category, String description,
                                   String location, String dateReported, int userId,
                                   String contactInfo, String imageUrl) {
        try {
            return itemService.reportLostItem(itemName, category, description,
                                              location, dateReported, userId, contactInfo, imageUrl);
        } catch (CustomException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    // Passes found item data including image URL to service layer
    public boolean reportFoundItem(String itemName, String category, String description,
                                    String location, String dateReported, int userId,
                                    String foundLocation, String imageUrl) {
        try {
            return itemService.reportFoundItem(itemName, category, description,
                                               location, dateReported, userId, foundLocation, imageUrl);
        } catch (CustomException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    // Returns list of all lost items
    public List<Item> getLostItems() {
        try {
            return itemService.getLostItems();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    // Returns list of all found items
    public List<Item> getFoundItems() {
        try {
            return itemService.getFoundItems();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<Item> getRecoveredItems() {
        try {
            return itemService.getRecoveredItems();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    public boolean submitClaim(int itemId, int claimerId, String message) {
        try {
            return itemService.submitClaim(itemId, claimerId, message);
        } catch (CustomException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean markItemRecovered(int itemId) {
        try {
            return itemService.markItemRecovered(itemId);
        } catch (CustomException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean markItemAsLost(int itemId) {
        try {
            return itemService.markItemAsLost(itemId);
        } catch (CustomException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<Item> getItemsByCategory(String status, String category) {
        try {
            return itemService.getItemsByCategory(status, category);
        } catch (CustomException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<Item> getItemsByUserId(int userId) {
        try {
            return itemService.getItemsByUserId(userId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }
}
