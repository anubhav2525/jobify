package com.naukari.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SiteController {
    // Return the welcome.html view from templates directory
    @GetMapping("/")
    public String welcomeToSite() {
        return "welcome"; // Resolves to src/main/resources/templates/welcome.html
    }
}
