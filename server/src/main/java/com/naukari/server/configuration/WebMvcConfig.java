package com.naukari.server.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${image.path}")
    private String imagePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve image files from file system at /uploads/images/** URL
        registry.addResourceHandler("/uploads/images/**")
                .addResourceLocations("file:" + imagePath); // e.g., "file:uploads/images/"
    }
}
