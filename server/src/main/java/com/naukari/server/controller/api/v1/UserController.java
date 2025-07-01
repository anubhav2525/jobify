package com.naukari.server.controller.api.v1;

import com.naukari.server.model.dto.user.ImageHandleDTO;
import com.naukari.server.model.dto.user.UserUpdateDTO;
import com.naukari.server.service.implementation.user.UserServiceImpl;
import com.naukari.server.utils.CreateResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CreateResponseEntity createResponseEntity;

    /**
     * Get the user's detail by email
     */
    @GetMapping("/me")
    public ResponseEntity<?> getUserByEmail() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return createResponseEntity.createResponse(userService.getUserByEmail(email));
    }

    /**
     * Get the user's detail by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return createResponseEntity.createResponse(userService.getUserById(id));
    }

    /**
     * Get all the users only for admin
     */
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        return createResponseEntity.createResponse(userService.getAllUsers());
    }

    /**
     * Get the user's detail
     */
    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateDTO dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return createResponseEntity.createResponse(userService.updateUserByEmail(email, dto));
    }

    /**
     * Upload profile image for company admin and user
     */
    @PostMapping(path = "/upload-profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadProfileImage(@ModelAttribute ImageHandleDTO dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return createResponseEntity.createResponse(userService.uploadProfileImage(email, dto.getImage()));
    }

    /**
     * Update profile image for company admin and user
     */
    @PutMapping(path = "/update-profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateProfileImage(@ModelAttribute ImageHandleDTO dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return createResponseEntity.createResponse(userService.updateProfileImage(email, dto.getImage()));
    }

    /**
     * Deactivate user account for company admin and user
     */
    @PutMapping("/deactivate")
    public ResponseEntity<?> deactivateUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return createResponseEntity.createResponse(userService.deactivateUser(email));
    }

    /**
     * Delete user account for company admin and user
     */
    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return createResponseEntity.createResponse(userService.deleteUser(email));
    }
}
