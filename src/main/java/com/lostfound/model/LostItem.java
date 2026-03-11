package com.lostfound.model;

// Unit 3 - Inheritance: Child class extending parent Item
public class LostItem extends Item {

    // Unit 1 - Extra field specific to lost items
    private String contactInfo;

    // Unit 1 - Default constructor
    public LostItem() {}

    // Unit 3 - Constructor chaining using super()
    public LostItem(int itemId, String itemName, String category, String description,
                    String location, String dateReported, int userId, String contactInfo) {
        super(itemId, itemName, category, description, location, dateReported, "lost", userId);
        this.contactInfo = contactInfo;
    }

    // Unit 3 - Constructor chaining without itemId (for new reports)
    public LostItem(String itemName, String category, String description,
                    String location, String dateReported, int userId, String contactInfo) {
        super(itemName, category, description, location, dateReported, "lost", userId);
        this.contactInfo = contactInfo;
    }

    // Unit 4 - Implementing abstract method from parent
    @Override
    public String getItemType() {
        return "Lost Item";
    }

    // Unit 3 - Method overriding
    @Override
    public String getDetails() {
        return super.getDetails() + ", Contact=" + contactInfo;
    }

    // Unit 1 - Getter and Setter
    public String getContactInfo() { return contactInfo; }
    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }
}