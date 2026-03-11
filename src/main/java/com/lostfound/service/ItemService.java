package com.lostfound.service;

import com.lostfound.model.Item;
import com.lostfound.model.LostItem;
import com.lostfound.model.FoundItem;
import com.lostfound.repository.ItemRepository;
import com.lostfound.util.CustomException;

import java.util.List;

// Unit 4 - Abstraction through service layer
public class ItemService {

    // Unit 1 - Instance variable
    private ItemRepository itemRepository;

    // Unit 1 - Constructor
    public ItemService() {
        this.itemRepository = new ItemRepository();
    }

    // Report a lost item
    public boolean reportLostItem(String itemName, String category, String description,
                                   String location, String dateReported, int userId,
                                   String contactInfo) throws CustomException {
        // Unit 2 - String validation
        if (itemName == null || itemName.isEmpty()) {
            throw new CustomException("Item name cannot be empty!");
        }
        if (location == null || location.isEmpty()) {
            throw new CustomException("Location cannot be empty!");
        }

        // Unit 3 - Creating child class object
        LostItem item = new LostItem(itemName, category, description,
                                     location, dateReported, userId, contactInfo);
        return itemRepository.reportItem(item);
    }

    // Report a found item
    public boolean reportFoundItem(String itemName, String category, String description,
                                    String location, String dateReported, int userId,
                                    String foundLocation) throws CustomException {
        if (itemName == null || itemName.isEmpty()) {
            throw new CustomException("Item name cannot be empty!");
        }
        if (location == null || location.isEmpty()) {
            throw new CustomException("Location cannot be empty!");
        }

        // Unit 3 - Creating child class object
        FoundItem item = new FoundItem(itemName, category, description,
                                       location, dateReported, userId, foundLocation);
        return itemRepository.reportItem(item);
    }

    // Unit 8 - Java 8 Stream API to get lost items
    public List<Item> getLostItems() {
        return itemRepository.getAllLostItems();
    }

    // Get found items
    public List<Item> getFoundItems() {
        return itemRepository.getAllFoundItems();
    }
}