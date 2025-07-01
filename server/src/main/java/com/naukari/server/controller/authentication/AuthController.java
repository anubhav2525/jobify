package com.naukari.server.controller.authentication;

import com.naukari.server.model.dto.user.*;
import com.naukari.server.model.enums.UserRole;
import com.naukari.server.service.implementation.user.UserServiceImpl;
import com.naukari.server.utils.CreateResponseEntity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CreateResponseEntity createResponseEntity;

    /**
     * Sign-in user
     */
    @PostMapping("/sign-in")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        return createResponseEntity.createResponse(userService.loginUser(userLoginDTO));
    }

    /**
     * Register user
     */
    @PostMapping("/sign-up")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        userRegistrationDTO.setRole(UserRole.CANDIDATE);
        return createResponseEntity.createResponse(userService.createUser(userRegistrationDTO));
    }

    /**
     * Register company admin
     */
    @PostMapping("/company/sign-up")
    public ResponseEntity<?> registerCompany(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        userRegistrationDTO.setRole(UserRole.COMPANY_ADMIN);
        return createResponseEntity.createResponse(userService.createUser(userRegistrationDTO));
    }

    /**
     * Verify email for company admin and user
     */
    @PostMapping("/verify-email/{email}")
    public ResponseEntity<?> verifyEmail(@Valid @RequestBody UserVerificationDTO userVerificationDTO, @PathVariable(name = "email") String email) {
        return createResponseEntity.createResponse(userService.verifyEmail(email, userVerificationDTO));
    }

    /**
     * Forget account password for company admin and user
     */
    @PostMapping("/forget-password/{email}")
    public ResponseEntity<?> forgetPassword(@PathVariable(name = "email") String email) {
        return createResponseEntity.createResponse(userService.forgetPassword(email));
    }

    /**
     * Update password for company admin and user
     */
    @PutMapping("/update-password/{email}")
    public ResponseEntity<?> updatePassword(@PathVariable(name = "email") String email, @RequestBody UserUpdatePasswordDTO userUpdatePasswordDTO) {
        return createResponseEntity.createResponse(userService.updatePassword(email, userUpdatePasswordDTO));
    }

    /**
     * Change password company admin and user
     */
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordDTO changePasswordDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return createResponseEntity.createResponse(userService.changePassword(email, changePasswordDTO));
    }
}