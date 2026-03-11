package com.lostfound.util;

// Unit 5 - Custom Exception (Checked Exception)
public class CustomException extends Exception {

    // Unit 1 - Constructor with message
    public CustomException(String message) {
        super(message);
    }

    // Unit 3 - Method overriding
    @Override
    public String getMessage() {
        return "LostFound Error: " + super.getMessage();
    }
}