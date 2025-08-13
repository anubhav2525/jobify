package com.naukari.server.controller.authentication;

import com.naukari.server.model.dto.user.*;
import com.naukari.server.model.enums.UserRole;
import com.naukari.server.service.implementation.user.UserServiceImpl;
import com.naukari.server.utils.CreateResponseEntity;
import com.naukari.server.utils.CustomResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
@Validated
@Tag(name = "Authentication", description = "Authentication and user management endpoints")
public class AuthController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CreateResponseEntity createResponseEntity;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    /**
     * Sign-in user
     */
    @PostMapping("/sign-in")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        logger.info("User login request received for email: {}", userLoginDTO.getEmail());

        CustomResponse<UserLoginResponseDTO> response = userService.loginUser(userLoginDTO);
        return createResponseEntity.createResponse(response);
    }

    /**
     * Register user
     */
    @PostMapping("/sign-up")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        logger.info("User registration request received for email: {}", userRegistrationDTO.getEmail());

        userRegistrationDTO.setRole(UserRole.CANDIDATE);
        CustomResponse<UserVerificationResponseDTO> response = userService.createUser(userRegistrationDTO);

        logger.info("User registration completed for email: {}", userRegistrationDTO.getEmail());
        return createResponseEntity.createResponse(response);
    }

    /**
     * Register company admin
     */
    @PostMapping("/company/sign-up")
    public ResponseEntity<?> registerCompany(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        logger.info("Company registration request received for email: {}", userRegistrationDTO.getEmail());

        userRegistrationDTO.setRole(UserRole.COMPANY_ADMIN);
        CustomResponse<UserVerificationResponseDTO> response = userService.createUser(userRegistrationDTO);

        logger.info("Company registration completed for email: {}", userRegistrationDTO.getEmail());
        return createResponseEntity.createResponse(response);
    }

    /**
     * Verify email for company admin and user
     */
    @PostMapping("/verify-email/{email}")
    public ResponseEntity<?> verifyEmail(@Valid @RequestBody UserVerificationDTO userVerificationDTO, @PathVariable(name = "email") String email) {
        logger.info("Verification request for email: {}", email);

        CustomResponse<UserVerificationResponseDTO> response = userService.verifyEmail(email, userVerificationDTO);

        logger.info("Verification request completed for email: {}", email);
        return createResponseEntity.createResponse(response);
    }

    /**
     * Forget account password for company admin and user
     */
    @PostMapping("/forget-password/{email}")
    public ResponseEntity<?> forgetPassword(@PathVariable(name = "email") String email) {
        logger.info("Forget password request for email: {}", email);

        CustomResponse<UserVerificationResponseDTO> response = userService.forgetPassword(email);

        logger.info("Forget password request completed for email: {}", email);
        return createResponseEntity.createResponse(response);
    }

    /**
     * Update password for company admin and user
     */
    @PutMapping("/update-password/{email}")
    public ResponseEntity<?> updatePassword(@PathVariable(name = "email") String email, @RequestBody UserUpdatePasswordDTO userUpdatePasswordDTO) {
        logger.info("Update password request for email: {}", email);

        CustomResponse<?> response = userService.updatePassword(email, userUpdatePasswordDTO);

        return createResponseEntity.createResponse(response);
    }

    /**
     * Change password company admin and user
     */
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordDTO changePasswordDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        logger.info("Change password request for email: {}", email);

        CustomResponse<?> response = userService.changePassword(email, changePasswordDTO);
        return createResponseEntity.createResponse(response);
    }
}