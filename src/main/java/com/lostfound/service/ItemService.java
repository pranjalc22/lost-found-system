package com.lostfound.service;

import com.lostfound.model.Item;
import com.lostfound.model.LostItem;
import com.lostfound.model.FoundItem;
import com.lostfound.repository.ItemRepository;
import com.lostfound.util.CustomException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

// Handles business logic and validation before saving to database
public class ItemService {

    private ItemRepository itemRepository;

    public ItemService() {
        this.itemRepository = new ItemRepository();
    }

    // Validates and creates a LostItem then saves to database
    public boolean reportLostItem(String itemName, String category, String description,
                                   String location, String dateReported, int userId,
                                   String contactInfo, String imageUrl) throws CustomException {
        if (itemName == null || itemName.isEmpty()) {
            throw new CustomException("Item name cannot be empty!");
        }
        if (location == null || location.isEmpty()) {
            throw new CustomException("Location cannot be empty!");
        }

        LostItem item = new LostItem(itemName, category, description,
                                     location, dateReported, userId, contactInfo);
        item.setImagePath(imageUrl);
        return itemRepository.reportItem(item);
    }

    // Validates and creates a FoundItem then saves to database
    public boolean reportFoundItem(String itemName, String category, String description,
                                    String location, String dateReported, int userId,
                                    String foundLocation, String imageUrl) throws CustomException {
        if (itemName == null || itemName.isEmpty()) {
            throw new CustomException("Item name cannot be empty!");
        }
        if (location == null || location.isEmpty()) {
            throw new CustomException("Location cannot be empty!");
        }

        FoundItem item = new FoundItem(itemName, category, description,
                                       location, dateReported, userId, foundLocation);
        item.setImagePath(imageUrl);
        return itemRepository.reportItem(item);
    }

    // Returns all lost items from database
    public List<Item> getLostItems() {
        return itemRepository.getAllLostItems()
                .stream()
                .filter(item -> "lost".equalsIgnoreCase(item.getStatus()))
                .collect(Collectors.toList());
    }

    // Returns all found items from database
    public List<Item> getFoundItems() {
        return itemRepository.getAllFoundItems()
                .stream()
                .filter(item -> "found".equalsIgnoreCase(item.getStatus()))
                .collect(Collectors.toList());
    }

    public List<Item> getRecoveredItems() {
        return itemRepository.getAllRecoveredItems();
    }

    public boolean submitClaim(int itemId, int claimerId, String message) throws CustomException {
        if (message == null || message.trim().isEmpty()) {
            throw new CustomException("Claim message cannot be empty!");
        }
        return itemRepository.saveClaim(itemId, claimerId, message.trim(), LocalDate.now().toString());
    }

    public boolean markItemRecovered(int itemId) throws CustomException {
        return itemRepository.markItemAsRecovered(itemId);
    }

    public boolean markItemAsLost(int itemId) throws CustomException {
        return itemRepository.markItemAsLost(itemId);
    }

    public List<Item> getItemsByCategory(String status, String category) throws CustomException {
        return itemRepository.getAllItemsByCategory(status, category);
    }

    public List<Item> getItemsByUserId(int userId) {
        return itemRepository.getItemsByUserId(userId);
    }
}
