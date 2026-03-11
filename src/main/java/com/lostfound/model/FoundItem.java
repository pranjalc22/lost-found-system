package com.lostfound.model;

// Unit 3 - Inheritance: Child class extending parent Item
public class FoundItem extends Item {

    // Unit 1 - Extra field specific to found items
    private String foundLocation;

    // Unit 1 - Default constructor
    public FoundItem() {}

    // Unit 3 - Constructor chaining using super()
    public FoundItem(int itemId, String itemName, String category, String description,
                     String location, String dateReported, int userId, String foundLocation) {
        super(itemId, itemName, category, description, location, dateReported, "found", userId);
        this.foundLocation = foundLocation;
    }

    // Unit 3 - Constructor chaining without itemId (for new reports)
    public FoundItem(String itemName, String category, String description,
                     String location, String dateReported, int userId, String foundLocation) {
        super(itemName, category, description, location, dateReported, "found", userId);
        this.foundLocation = foundLocation;
    }

    // Unit 4 - Implementing abstract method from parent
    @Override
    public String getItemType() {
        return "Found Item";
    }

    // Unit 3 - Method overriding
    @Override
    public String getDetails() {
        return super.getDetails() + ", Found At=" + foundLocation;
    }

    // Unit 1 - Getter and Setter
    public String getFoundLocation() { return foundLocation; }
    public void setFoundLocation(String foundLocation) { this.foundLocation = foundLocation; }
}