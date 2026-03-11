package com.lostfound.controller;

import com.lostfound.model.Item;
import com.lostfound.service.ItemService;
import com.lostfound.util.CustomException;

import java.util.List;
import java.util.ArrayList;

// MVC - Controller layer
public class ItemController {

    // Unit 1 - Instance variable
    private ItemService itemService;

    // Unit 1 - Constructor
    public ItemController() {
        this.itemService = new ItemService();
    }

    // Handle reporting a lost item
    public boolean reportLostItem(String itemName, String category, String description,
                                   String location, String dateReported, int userId,
                                   String contactInfo) {
        try {
            return itemService.reportLostItem(itemName, category, description,
                                              location, dateReported, userId, contactInfo);
        } catch (CustomException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    // Handle reporting a found item
    public boolean reportFoundItem(String itemName, String category, String description,
                                    String location, String dateReported, int userId,
                                    String foundLocation) {
        try {
            return itemService.reportFoundItem(itemName, category, description,
                                               location, dateReported, userId, foundLocation);
        } catch (CustomException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    // Unit 5 - ArrayList, Unit 8 - Java 8 streams in service layer
    public List<Item> getLostItems() {
        try {
            return itemService.getLostItems();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<Item> getFoundItems() {
        try {
            return itemService.getFoundItems();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }
}