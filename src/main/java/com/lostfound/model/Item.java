package com.lostfound.model;

// Unit 3 - Inheritance: Parent class Item
// Unit 4 - Abstraction: Abstract class
public abstract class Item {

    // Unit 1 - Access modifiers: protected so child classes can access
    protected int itemId;
    protected String itemName;
    protected String category;
    protected String description;
    protected String location;
    protected String dateReported;
    protected String status; // "lost" or "found"
    protected int userId;

    // Unit 1 - Default constructor
    public Item() {}

    // Unit 1 - Parameterized constructor + this keyword
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

    // Unit 1 - Constructor without itemId (for reporting new item)
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

    // Unit 4 - Abstract method (must be implemented by child classes)
    public abstract String getItemType();

    // Unit 3 - Method overriding will happen in child classes
    public String getDetails() {
        return "Item [ID=" + itemId + ", Name=" + itemName + ", Category=" + category +
               ", Location=" + location + ", Date=" + dateReported + ", Status=" + status + "]";
    }

    // Unit 1 - Getters and Setters
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
}