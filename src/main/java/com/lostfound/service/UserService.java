package com.lostfound.service;

import com.lostfound.model.User;
import com.lostfound.repository.UserRepository;
import com.lostfound.util.CustomException;

// Unit 4 - Abstraction through service layer
// Unit 5 - Custom Exception handling
public class UserService {

    // Unit 1 - Instance variable
    private UserRepository userRepository;

    // Unit 1 - Constructor
    public UserService() {
        this.userRepository = new UserRepository();
    }

    // Unit 5 - Checked exception handling
    public boolean register(String name, String email, String phone, String password) throws CustomException {
        // Unit 2 - String validation
        if (name == null || name.isEmpty()) {
            throw new CustomException("Name cannot be empty!");
        }
        if (email == null || email.isEmpty()) {
            throw new CustomException("Email cannot be empty!");
        }
        if (password == null || password.length() < 4) {
            throw new CustomException("Password must be at least 4 characters!");
        }

        User user = new User(name, email, phone, password);
        return userRepository.registerUser(user);
    }

    // Unit 5 - Exception handling for login
    public User login(String email, String password) throws CustomException {
        if (email == null || email.isEmpty()) {
            throw new CustomException("Email cannot be empty!");
        }
        if (password == null || password.isEmpty()) {
            throw new CustomException("Password cannot be empty!");
        }

        User user = userRepository.loginUser(email, password);
        if (user == null) {
            throw new CustomException("Invalid email or password!");
        }
        return user;
    }
}