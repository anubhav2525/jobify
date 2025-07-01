package com.naukari.server.utils;

import org.springframework.stereotype.Component;

@Component
public class CommonServices {
    // Helper method to validate password strength
    private boolean isValidPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasUpperCase = password.matches(".*[A-Z].*");
        boolean hasLowerCase = password.matches(".*[a-z].*");
        boolean hasNumber = password.matches(".*\\d.*");
        boolean hasSpecialChar = password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");

        return hasUpperCase && hasLowerCase && hasNumber && hasSpecialChar;
    }

    // Helper method to validate phone number
    private boolean isValidPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        // Basic phone validation - adjust regex as per your requirements
        return phone.matches("^[+]?[1-9]\\d{1,14}$");
    }

    // Helper method to validate image URL
    private boolean isValidImageUrl(String imageUrl) {
        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            return false;
        }
        // Basic URL validation for images
        return imageUrl.matches("^https?://.*\\.(jpg|jpeg|png|gif|bmp|webp)$");
    }
}
