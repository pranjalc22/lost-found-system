package com.lostfound.model;

// Abstract parent class for LostItem and FoundItem
public abstract class Item {

    protected int itemId;
    protected String itemName;
    protected String category;
    protected String description;
    protected String location;
    protected String dateReported;
    protected String status; // "lost" or "found"
    protected int userId;
    protected String imagePath; // Cloudinary URL of item image
    protected boolean recovered;

    // Default constructor
    public Item() {}

    // Constructor for fetching existing item from DB (includes itemId)
    public Item(int itemId, String itemName, String category, String description,
                String location, String dateReported, String status, int userId) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.category = category;
        this.description = description;
        this.location = location;
        this.dateReported = dateReported;
        this.status = status;
        this.userId = userId;
    }

    // Constructor for reporting a new item (no itemId yet)
    public Item(String itemName, String category, String description,
                String location, String dateReported, String status, int userId) {
        this.itemName = itemName;
        this.category = category;
        this.description = description;
        this.location = location;
        this.dateReported = dateReported;
        this.status = status;
        this.userId = userId;
    }

    // Must be implemented by child classes
    public abstract String getItemType();

    // Can be overridden by child classes
    public String getDetails() {
        return "Item [ID=" + itemId + ", Name=" + itemName + ", Category=" + category +
               ", Location=" + location + ", Date=" + dateReported + ", Status=" + status + "]";
    }

    // Getters and Setters
    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getDateReported() { return dateReported; }
    public void setDateReported(String dateReported) { this.dateReported = dateReported; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public boolean isRecovered() { return recovered; }
    public void setRecovered(boolean recovered) { this.recovered = recovered; }
}
