package com.naukari.server.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
public class ImageHandler {
    @Value("${image.path}")
    private String imagePath;

    @Value("${image.max.size}") // 5MB default
    private long maxImageSize;

    @Value("${server.base.url}")
    private String baseUrl;

    private static final List<String> ALLOWED_IMAGE_EXTENSIONS = Arrays.asList(
            "jpg", "jpeg", "png", "gif", "bmp", "webp"
    );

    private static final List<String> ALLOWED_MIME_TYPES = Arrays.asList(
            "image/jpeg", "image/jpg", "image/png", "image/gif",
            "image/bmp", "image/webp"
    );

    /**
     * Save uploaded image to file system
     */
    public String saveImage(MultipartFile image, String subDirectory) throws IOException {
        validateImage(image);

        // Create directory structure
        String fullPath = imagePath + (subDirectory != null ? subDirectory + "/" : "");
        createDirectoryIfNotExists(fullPath);

        // Generate unique filename
        String originalFilename = image.getOriginalFilename();
        String fileExtension = getFileExtension(originalFilename);
        String uniqueFilename = generateUniqueFilename(fileExtension);

        // Full file path
        Path filePath = Paths.get(fullPath + uniqueFilename);

        // Save file
        Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Return relative URL
        return "/uploads/images/" + (subDirectory != null ? subDirectory + "/" : "") + uniqueFilename;
    }

    /**
     * Save image with default directory (profile images)
     */
    public String saveImage(MultipartFile image) throws IOException {
        return saveImage(image, "profiles");
    }

    /**
     * Update existing image - deletes old file and saves new one
     */
    public String updateImage(MultipartFile newImage, String oldImageUrl, String subDirectory) throws IOException {
        // Delete old image if exists
        if (oldImageUrl != null && !oldImageUrl.trim().isEmpty()) {
            deleteImageByUrl(oldImageUrl);
        }

        // Save new image
        return saveImage(newImage, subDirectory);
    }

    /**
     * Update image with default directory
     */
    public String updateImage(MultipartFile newImage, String oldImageUrl) throws IOException {
        return updateImage(newImage, oldImageUrl, "profiles");
    }

    /**
     * Delete image by URL
     */
    public boolean deleteImageByUrl(String imageUrl) {
        try {
            if (imageUrl == null || imageUrl.trim().isEmpty()) {
                return false;
            }

            // Extract relative path from URL
            String relativePath = imageUrl.startsWith("/") ? imageUrl.substring(1) : imageUrl;

            // Remove base URL if present
            if (relativePath.startsWith("http")) {
                relativePath = relativePath.substring(relativePath.indexOf("/uploads/"));
                relativePath = relativePath.startsWith("/") ? relativePath.substring(1) : relativePath;
            }

            // Construct full file path
            Path filePath = Paths.get(relativePath);

            // Delete file
            return Files.deleteIfExists(filePath);

        } catch (Exception e) {
            System.err.println("Error deleting image: " + e.getMessage());
            return false;
        }
    }

    /**
     * Delete image by filename
     */
    public boolean deleteImage(String filename, String subDirectory) {
        try {
            String fullPath = imagePath + (subDirectory != null ? subDirectory + "/" : "") + filename;
            Path filePath = Paths.get(fullPath);
            return Files.deleteIfExists(filePath);
        } catch (IOException e) {
            System.err.println("Error deleting image: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get image URL from filename
     */
    public String getImageUrl(String filename, String subDirectory) {
        if (filename == null || filename.trim().isEmpty()) {
            return "";
        }

        String relativePath = "/uploads/images/" + (subDirectory != null ? subDirectory + "/" : "") + filename;
        return baseUrl + relativePath;
    }

    /**
     * Get image URL with default directory
     */
    public String getImageUrl(String filename) {
        return getImageUrl(filename, "profiles");
    }

    /**
     * Get default image URL
     */
    public String getImageUrl() {
        return baseUrl + "/uploads/images/default-profile.png";
    }

    /**
     * Check if image exists
     */
    public boolean imageExists(String filename, String subDirectory) {
        String fullPath = imagePath + (subDirectory != null ? subDirectory + "/" : "") + filename;
        return Files.exists(Paths.get(fullPath));
    }

    /**
     * Get image file size
     */
    public long getImageSize(String filename, String subDirectory) {
        try {
            String fullPath = imagePath + (subDirectory != null ? subDirectory + "/" : "") + filename;
            Path filePath = Paths.get(fullPath);
            return Files.size(filePath);
        } catch (IOException e) {
            return 0;
        }
    }

    /**
     * Validate uploaded image
     */
    private void validateImage(MultipartFile image) throws IOException {
        if (image == null || image.isEmpty()) {
            throw new IOException("Image file is required");
        }

        // Check file size
        if (image.getSize() > maxImageSize) {
            throw new IOException("Image size exceeds maximum allowed size of " + (maxImageSize / 1024 / 1024) + "MB");
        }

        // Check MIME type
        String contentType = image.getContentType();
        if (contentType == null || !ALLOWED_MIME_TYPES.contains(contentType.toLowerCase())) {
            throw new IOException("Invalid image format. Allowed formats: " + String.join(", ", ALLOWED_IMAGE_EXTENSIONS));
        }

        // Check file extension
        String filename = image.getOriginalFilename();
        if (filename == null) {
            throw new IOException("Invalid filename");
        }

        String fileExtension = getFileExtension(filename);
        if (!ALLOWED_IMAGE_EXTENSIONS.contains(fileExtension.toLowerCase())) {
            throw new IOException("Invalid file extension. Allowed extensions: " + String.join(", ", ALLOWED_IMAGE_EXTENSIONS));
        }
    }

    /**
     * Get file extension from filename
     */
    private String getFileExtension(String filename) {
        if (filename == null || filename.lastIndexOf('.') == -1) {
            return "";
        }
        return filename.substring(filename.lastIndexOf('.') + 1);
    }

    /**
     * Generate unique filename
     */
    private String generateUniqueFilename(String extension) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        return timestamp + "_" + uuid + "." + extension;
    }

    /**
     * Create directory if it doesn't exist
     */
    private void createDirectoryIfNotExists(String directoryPath) throws IOException {
        Path path = Paths.get(directoryPath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }

    /**
     * Clean up old images (utility method for scheduled cleanup)
     */
    public void cleanupOldImages(String subDirectory, int daysOld) {
        try {
            String fullPath = imagePath + (subDirectory != null ? subDirectory + "/" : "");
            Path directory = Paths.get(fullPath);

            if (Files.exists(directory)) {
                Files.walk(directory)
                        .filter(Files::isRegularFile)
                        .filter(path -> {
                            try {
                                long fileAge = System.currentTimeMillis() - Files.getLastModifiedTime(path).toMillis();
                                long daysInMillis = daysOld * 24 * 60 * 60 * 1000L;
                                return fileAge > daysInMillis;
                            } catch (IOException e) {
                                return false;
                            }
                        })
                        .forEach(path -> {
                            try {
                                Files.deleteIfExists(path);
                                System.out.println("Deleted old image: " + path.getFileName());
                            } catch (IOException e) {
                                System.err.println("Error deleting old image: " + e.getMessage());
                            }
                        });
            }
        } catch (IOException e) {
            System.err.println("Error during cleanup: " + e.getMessage());
        }
    }

    /**
     * Helper method to extract filename from URL
     */
    private String extractFilenameFromUrl(String url) {
        if (url == null || url.trim().isEmpty()) {
            return "";
        }
        return url.substring(url.lastIndexOf('/') + 1);
    }
}
