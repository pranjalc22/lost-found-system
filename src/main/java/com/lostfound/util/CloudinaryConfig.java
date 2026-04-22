package com.lostfound.util;

import com.cloudinary.Cloudinary;
import java.util.HashMap;
import java.util.Map;

// Handles Cloudinary connection and image uploads
public class CloudinaryConfig {

    private static Cloudinary cloudinary;

    // Returns single Cloudinary instance (Singleton)
    public static Cloudinary getInstance() {
        if (cloudinary == null) {
            Map<String, String> config = new HashMap<>();
            config.put("cloud_name", "diywwygdn");
            config.put("api_key", "743152393152324");
            config.put("api_secret", "k1MPE3GhHLC1xJ3e_J8yWMaiRiY");
            cloudinary = new Cloudinary(config);
        }
        return cloudinary;
    }

    // Uploads image from local file path and returns Cloudinary URL
    public static String uploadImage(String filePath) {
        try {
            Map uploadResult = getInstance().uploader().upload(filePath, new HashMap<>());
            return uploadResult.get("secure_url").toString();
        } catch (Exception e) {
            System.out.println("Cloudinary upload failed for file: " + filePath);
            System.out.println("Exception type: " + e.getClass().getName());
            System.out.println("Error message: " + e.getMessage());
            e.printStackTrace(System.out);
            FileLogger.log("Cloudinary upload failed: " + e.getMessage());
            return null;
        }
    }
}
