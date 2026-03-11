package com.lostfound.util;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

// Unit 6 - File Handling for logs
public class FileLogger {

    // Unit 1 - Static variable
    private static final String LOG_FILE = "lostfound_log.txt";

    // Unit 1 - Private constructor
    private FileLogger() {}

    // Unit 1 - Static method
    public static void log(String message) {
        try {
            // Unit 6 - FileWriter to write to file
            FileWriter writer = new FileWriter(LOG_FILE, true);
            writer.write("[" + LocalDateTime.now() + "] " + message + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Logging failed: " + e.getMessage());
        }
    }
}