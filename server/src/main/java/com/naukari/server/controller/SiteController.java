package com.naukari.server.controller;

import com.naukari.server.utils.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SiteController {

    @Autowired
    private EmailService emailService;

    // Return the welcome.html view from templates directory
    @GetMapping("/")
    public String welcomeToSite() {
        return "welcome"; // Resolves to src/main/resources/templates/welcome.html
    }

    @PostMapping("/sample-mail")
    public ResponseEntity<?> sendSampleEmail() {
        EmailService.Response<Void> response = emailService.sendEmail("infohuntofficial@gmail.com", "Sample mail by Spring boot", "Hello from naukari");

        if (response.getStatus().equals("Success")) return new ResponseEntity<>(response, HttpStatus.OK);
        else return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
