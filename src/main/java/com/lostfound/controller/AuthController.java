package com.lostfound.controller;

import com.lostfound.model.User;
import com.lostfound.service.UserService;
import com.lostfound.util.CustomException;

// Unit 1 - OOP: Class, Objects
// MVC - Controller layer connects GUI to Service
public class AuthController {

    // Unit 1 - Instance variable
    private UserService userService;

    // Unit 1 - Constructor
    public AuthController() {
        this.userService = new UserService();
    }

    // Handle registration
    public boolean register(String name, String email, String phone, String password) {
        try {
            return userService.register(name, email, phone, password);
        } catch (CustomException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    // Handle login - returns User object if successful, null if not
    public User login(String email, String password) {
        try {
            return userService.login(email, password);
        } catch (CustomException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}