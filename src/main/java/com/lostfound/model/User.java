package com.lostfound.model;

// Unit 1 - OOP Basics: Class, Object, Constructor, Access Modifiers, this keyword
public class User {

    // Unit 1 - Access modifiers: private fields
    private int userId;
    private String name;
    private String email;
    private String phone;
    private String password;

    // Unit 1 - Default constructor
    public User() {}

    // Unit 1 - Parameterized constructor + this keyword
    public User(int userId, String name, String email, String phone, String password) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    // Unit 1 - Constructor without userId (for registration)
    public User(String name, String email, String phone, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    // Unit 1 - Getters and Setters (instance methods)
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    // Unit 2 - Method returning value
    @Override
    public String toString() {
        return "User [ID=" + userId + ", Name=" + name + ", Email=" + email + "]";
    }
}