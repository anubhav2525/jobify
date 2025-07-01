package com.naukari.server.service.implementation.user;

import com.naukari.server.model.dto.user.*;
import com.naukari.server.model.entity.user.User;
import com.naukari.server.model.enums.ResponseStatus;
import com.naukari.server.repository.user.UserRepo;
import com.naukari.server.security.JwtUtil;
import com.naukari.server.service.user.UserService;
import com.naukari.server.utils.CustomResponse;
import com.naukari.server.utils.EmailService;
import com.naukari.server.utils.ImageHandler;
import com.naukari.server.utils.RandomStringGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepo userRepo;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final JwtUtil jwtUtil;

    @Autowired
    private final ModelMapper modelMapper;

    @Autowired
    private ImageHandler imageHandler;

    @Autowired
    private final RandomStringGenerator randomStringGenerator;

    private final EmailService emailService;

    public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtil jwtUtil, ModelMapper modelMapper, RandomStringGenerator randomStringGenerator, EmailService emailService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.modelMapper = modelMapper;
        this.randomStringGenerator = randomStringGenerator;
        this.emailService = emailService;
    }

    @Override
    public CustomResponse<UserVerificationResponseDTO> createUser(UserRegistrationDTO userRegistration) {
        try {
            // Check if user already exists
            if (userRepo.existsByEmail(userRegistration.getEmail()))
                return new CustomResponse<>(ResponseStatus.ALREADY_EXISTS,
                        "User with this email already exists", null);

            // Create new user
            User user = new User();
            user.setEmail(userRegistration.getEmail().toLowerCase().trim());
            user.setPassword(passwordEncoder.encode(userRegistration.getPassword().trim()));
            user.setFirstName(userRegistration.getFirstName().trim());
            user.setLastName(userRegistration.getLastName().trim());
            user.setMiddleName(userRegistration.getMiddleName().trim());
            user.setPhone(userRegistration.getPhone().trim());
            user.setCountryCode(userRegistration.getCountryCode().trim());
            user.setRole(userRegistration.getRole());
            user.setActive(true);

            // Send verification email
            String fullName = user.getFirstName() + " " + user.getLastName();
            String otp = randomStringGenerator.generate(6);

            // Store OTP temporarily (you might want to save this in the user entity)
            user.setToken(otp);
            user.setTokenExpirationAt(LocalDateTime.now().plusMinutes(15));
            User savedUser = userRepo.save(user);

            // Send verification email
            EmailService.Response<Void> emailResponse = emailService.sendAccountVerificationEmail(
                    user.getEmail(), fullName, otp);

            UserVerificationResponseDTO responseDTO = convertToVerificationDTO(savedUser);
            if ("Success".equals(emailResponse.getStatus())) {
                return new CustomResponse<>(ResponseStatus.CREATED,
                        "User created successfully. Verification email sent.", responseDTO);
            } else {
                return new CustomResponse<>(ResponseStatus.CREATED,
                        "User created successfully, but failed to send verification email: " +
                                emailResponse.getMessage(), responseDTO);
            }
        } catch (Exception e) {
            return new CustomResponse<>(ResponseStatus.INTERNAL_ERROR,
                    "Failed to create user: " + e.getMessage(), null);
        }
    }

    @Override
    public CustomResponse<UserVerificationResponseDTO> verifyEmail(String email, UserVerificationDTO userVerificationDTO) {
        try {
            if (email == null || email.trim().isEmpty())
                return new CustomResponse<>(ResponseStatus.BAD_REQUEST, "Email is required", null);

            User user = userRepo.findByEmail(email).orElse(null);
            if (user == null) return new CustomResponse<>(ResponseStatus.NOT_FOUND, "User not found", null);

            if (!user.isActive())
                return new CustomResponse<>(ResponseStatus.UNAUTHORIZED, "User account is deactivated", null);

            if (user.isEmailVerified())
                return new CustomResponse<>(ResponseStatus.ALREADY_EXISTS, "Email is already verified", null);

            if (!user.getToken().equals(userVerificationDTO.getToken().trim()))
                return new CustomResponse<>(ResponseStatus.BAD_REQUEST, "Token is incorrect", null);

            user.setEmailVerifiedAt(LocalDateTime.now());
            user.setEmailVerified(true);
            user.setTokenExpirationAt(null);
            user.setToken(null);
            user.setUpdatedAt(LocalDateTime.now());
            userRepo.save(user);

            // Send welcome email
            EmailService.Response<Void> emailResponse = emailService.sendWelcomeEmail(user.getEmail(), user.getFirstName());

            if ("Success".equals(emailResponse.getStatus()))
                return new CustomResponse<>(ResponseStatus.CREATED,
                        "User verified", null);
            else
                return new CustomResponse<>(ResponseStatus.CREATED,
                        "User verified, but failed to send verification email: " +
                                emailResponse.getMessage(), null);
        } catch (Exception e) {
            return new CustomResponse<>(ResponseStatus.INTERNAL_ERROR,
                    "Failed to verify email: " + e.getMessage(), null);
        }
    }

    @Override
    public CustomResponse<UserResponseDTO> getUserById(Long id) {
        try {
            if (id == null || id <= 0) {
                return new CustomResponse<>(ResponseStatus.BAD_REQUEST, "Invalid user ID", null);
            }

            User user = userRepo.findById(id).orElse(null);
            if (user == null) {
                return new CustomResponse<>(ResponseStatus.NOT_FOUND, "User not found", null);
            }

            // Check if user is active
            if (!user.isActive()) {
                return new CustomResponse<>(ResponseStatus.UNAUTHORIZED, "User account is deactivated", null);
            }

            UserResponseDTO userResponse = convertToResponseDTO(user);
            return new CustomResponse<>(ResponseStatus.SUCCESS, "User found", userResponse);
        } catch (Exception e) {
            return new CustomResponse<>(ResponseStatus.INTERNAL_ERROR,
                    "Failed to get user by " + id + " : " + e.getMessage(), null);
        }
    }

    @Override
    public CustomResponse<UserResponseDTO> getUserByEmail(String email) {
        try {
            if (email == null || email.trim().isEmpty()) {
                return new CustomResponse<>(ResponseStatus.BAD_REQUEST, "Email is required", null);
            }

            User user = userRepo.findByEmail(email.toLowerCase().trim()).orElse(null);
            if (user == null) {
                return new CustomResponse<>(ResponseStatus.NOT_FOUND, "User not found", null);
            }

            // Check if user is active
            if (!user.isActive()) {
                return new CustomResponse<>(ResponseStatus.UNAUTHORIZED, "User account is deactivated", null);
            }

            UserResponseDTO userResponse = convertToResponseDTO(user);
            return new CustomResponse<>(ResponseStatus.SUCCESS, "User found", userResponse);
        } catch (Exception e) {
            return new CustomResponse<>(ResponseStatus.INTERNAL_ERROR,
                    "Failed to get user by " + email + " : " + e.getMessage(), null);
        }
    }

    @Override
    public CustomResponse<List<UserResponseDTO>> getAllUsers() {
        try {


            List<User> users = userRepo.findAll();
            if (users.isEmpty())
                return new CustomResponse<>(ResponseStatus.NOT_FOUND, "No users found", null);

            List<UserResponseDTO> userResponses = users.stream()
                    .filter(User::isActive)
                    .map(this::convertToResponseDTO)
                    .toList();

            return new CustomResponse<>(ResponseStatus.SUCCESS, "Users found", userResponses);
        } catch (Exception e) {
            return new CustomResponse<>(ResponseStatus.INTERNAL_ERROR,
                    "Failed to get users: " + e.getMessage(), null);
        }
    }

    @Override
    public CustomResponse<UserResponseDTO> updateUserByEmail(String email, UserUpdateDTO updateDTO) {
        try {
            if (email == null || email.trim().isEmpty())
                return new CustomResponse<>(ResponseStatus.BAD_REQUEST, "Email is required", null);

            if (updateDTO == null)
                return new CustomResponse<>(ResponseStatus.BAD_REQUEST, "Update data is required", null);

            User user = userRepo.findByEmail(email).orElse(null);

            if (user == null)
                return new CustomResponse<>(ResponseStatus.NOT_FOUND, "User not found", null);

            // Check if user is active
            if (!user.isActive())
                return new CustomResponse<>(ResponseStatus.UNAUTHORIZED, "User account is deactivated", null);

            // Update user fields
            boolean isUpdated = false;

            // update user
            if (updateDTO.getFirstName() != null && !updateDTO.getFirstName().trim().equalsIgnoreCase(user.getFirstName())) {
                user.setFirstName(updateDTO.getFirstName().trim());
                isUpdated = true;
            }

            if (updateDTO.getLastName() != null && !updateDTO.getLastName().trim().equalsIgnoreCase(user.getLastName())) {
                user.setLastName(updateDTO.getLastName().trim());
                isUpdated = true;
            }

            if (updateDTO.getMiddleName() != null && !updateDTO.getMiddleName().trim().equalsIgnoreCase(user.getMiddleName())) {
                user.setMiddleName(updateDTO.getMiddleName().trim());
                isUpdated = true;
            }

            if (updateDTO.getPhone() != null && !updateDTO.getPhone().equals(user.getPhone())) {
                user.setPhone(updateDTO.getPhone().trim());
                isUpdated = true;
            }

            if (isUpdated) {
                user.setUpdatedAt(LocalDateTime.now());
                User updatedUser = userRepo.save(user);
                UserResponseDTO userResponseDTO = convertToResponseDTO(updatedUser);
                return new CustomResponse<>(ResponseStatus.SUCCESS, "User updated successfully", userResponseDTO);
            } else return new CustomResponse<>(ResponseStatus.NO_CONTENT, "No changes to update", null);
        } catch (Exception e) {
            return new CustomResponse<>(ResponseStatus.INTERNAL_ERROR,
                    "Failed to update users: " + e.getMessage(), null);
        }
    }

    @Override
    public CustomResponse<UserResponseDTO> changePassword(String email, ChangePasswordDTO passwordDTO) {
        try {
            if (email == null || email.trim().isEmpty())
                return new CustomResponse<>(ResponseStatus.BAD_REQUEST, "Email is required", null);

            User user = userRepo.findByEmail(email.toLowerCase().trim()).orElse(null);
            if (user == null) {
                return new CustomResponse<>(ResponseStatus.NOT_FOUND, "User not found", null);
            }

            // Check if user is active
            if (!user.isActive())
                return new CustomResponse<>(ResponseStatus.UNAUTHORIZED, "User account is deactivated", null);

            // Verify current password using password encoder
            if (!passwordEncoder.matches(passwordDTO.getCurrentPassword().trim(), user.getPassword()))
                return new CustomResponse<>(ResponseStatus.BAD_REQUEST, "Current password is incorrect", null);

            // Check if new password is different from current
            if (passwordEncoder.matches(passwordDTO.getNewPassword().trim(), user.getPassword()))
                return new CustomResponse<>(ResponseStatus.BAD_REQUEST, "New password must be different from current password", null);

            // Change password
            user.setPassword(passwordEncoder.encode(passwordDTO.getNewPassword().trim()));
            user.setUpdatedAt(LocalDateTime.now());

            // save to database
            userRepo.save(user);
            return new CustomResponse<>(ResponseStatus.SUCCESS, "Password changed", null);
        } catch (Exception e) {
            return new CustomResponse<>(ResponseStatus.INTERNAL_ERROR,
                    "Failed to change password: " + e.getMessage(), null);
        }
    }

    @Override
    public CustomResponse<String> deactivateUser(String email) {
        try {
            if (email == null || email.trim().isEmpty())
                return new CustomResponse<>(ResponseStatus.BAD_REQUEST, "Email is required", null);

            User user = userRepo.findByEmail(email).orElse(null);
            if (user == null)
                return new CustomResponse<>(ResponseStatus.NOT_FOUND, "User not found", null);

            if (!user.isActive())
                return new CustomResponse<>(ResponseStatus.ALREADY_EXISTS, "User is already deactivated", null);

            // Deactivate user
            user.setActive(false);
            user.setUpdatedAt(LocalDateTime.now());
            userRepo.save(user);
            return new CustomResponse<>(ResponseStatus.SUCCESS, "User deactivated successfully", "User account has been deactivated");
        } catch (Exception e) {
            return new CustomResponse<>(ResponseStatus.INTERNAL_ERROR,
                    "Failed to deactivate user: " + e.getMessage(), null);
        }
    }

    @Override
    public CustomResponse<String> deleteUser(String email) {
        try {
            if (email == null || email.trim().isEmpty()) {
                return new CustomResponse<>(ResponseStatus.BAD_REQUEST, "Email is required", null);
            }

            User user = userRepo.findByEmail(email).orElse(null);
            if (user == null) {
                return new CustomResponse<>(ResponseStatus.NOT_FOUND, "User not found", null);
            }

            // Perform soft delete by marking as deleted and deactivating
            user.setActive(false);
            user.setAccountDeleted(true);
            user.setAccountDeletedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());

            userRepo.save(user);
            return new CustomResponse<>(ResponseStatus.SUCCESS, "User deleted successfully", "User account has been deleted");
        } catch (Exception e) {
            return new CustomResponse<>(ResponseStatus.INTERNAL_ERROR,
                    "Failed to delete user: " + e.getMessage(), null);
        }
    }

    @Override
    public CustomResponse<UserLoginResponseDTO> loginUser(UserLoginDTO userLoginDTO) {
        try {
            if (userLoginDTO == null || userLoginDTO.getEmail() == null || userLoginDTO.getPassword() == null) {
                return new CustomResponse<>(ResponseStatus.BAD_REQUEST, "Email and password are required", null);
            }

            String email = userLoginDTO.getEmail().toLowerCase().trim();
            String password = userLoginDTO.getPassword().trim();

            // Find user by email
            User user = userRepo.findByEmail(email).orElse(null);
            if (user == null) {
                return new CustomResponse<>(ResponseStatus.BAD_REQUEST, "Invalid email", null);
            }

            // Check if user is active
            if (!user.isActive()) {
                return new CustomResponse<>(ResponseStatus.UNAUTHORIZED, "User account is deactivated", null);
            }

            // Verify password
            if (!passwordEncoder.matches(password, user.getPassword())) {
                return new CustomResponse<>(ResponseStatus.BAD_REQUEST, "Invalid password", null);
            }

            // Authenticate user
            Authentication authentication;
            try {
                authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(email, password)
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (BadCredentialsException e) {
                return new CustomResponse<>(ResponseStatus.UNAUTHORIZED, "Invalid email or password", null);
            }

            // Generate JWT token
            String token = jwtUtil.generateJwtToken(authentication);
//            String refreshToken = jwtUtil.generateRefreshToken(email);

            // Update last login time
            user.setLastLoginAt(LocalDateTime.now());
            userRepo.save(user);

            // Create response DTO
            UserLoginResponseDTO responseDTO = new UserLoginResponseDTO();
            responseDTO.setToken(token);
//            responseDTO.setRefreshToken(refreshToken);
//            responseDTO.setUser(convertToResponseDTO(user));
//            responseDTO.setExpiresIn(jwtUtil.getExpirationTime());

            return new CustomResponse<>(ResponseStatus.SUCCESS, "Login successful", responseDTO);
        } catch (Exception e) {
            return new CustomResponse<>(ResponseStatus.INTERNAL_ERROR,
                    "Login failed: " + e.getMessage(), null);
        }
    }

    @Override
    public CustomResponse<Boolean> isEmailVerified(String email) {
        try {
            if (email == null || email.trim().isEmpty()) {
                return new CustomResponse<>(ResponseStatus.BAD_REQUEST, "Email is required", null);
            }

            User user = userRepo.findByEmail(email).orElse(null);
            if (user == null) {
                return new CustomResponse<>(ResponseStatus.NOT_FOUND, "User not found", null);
            }

            if (!user.isActive()) {
                return new CustomResponse<>(ResponseStatus.UNAUTHORIZED, "User account is deactivated", null);
            }

            return new CustomResponse<>(ResponseStatus.SUCCESS, "Email verification status retrieved", user.isEmailVerified());
        } catch (Exception e) {
            return new CustomResponse<>(ResponseStatus.INTERNAL_ERROR,
                    "Failed to check email verification status: " + e.getMessage(), null);
        }
    }

    @Override
    public CustomResponse<String> uploadProfileImage(String email, MultipartFile image) {
        try {
            if (email == null || email.trim().isEmpty())
                return new CustomResponse<>(ResponseStatus.BAD_REQUEST, "Email is required", null);

            if (image == null || image.isEmpty())
                return new CustomResponse<>(ResponseStatus.BAD_REQUEST, "Image file is required", null);

            User user = userRepo.findByEmail(email.toLowerCase().trim()).orElse(null);
            if (user == null)
                return new CustomResponse<>(ResponseStatus.NOT_FOUND, "User not found", null);

            // Check if user is active
            if (!user.isActive())
                return new CustomResponse<>(ResponseStatus.UNAUTHORIZED, "User account is deactivated", null);

            String imageUrl = imageHandler.saveImage(image);
            user.setProfileImageUrl(imageUrl);
            user.setUpdatedAt(LocalDateTime.now());
            userRepo.save(user);
            return new CustomResponse<>(ResponseStatus.SUCCESS, "Profile image updated successfully", user.getProfileImageUrl());
        } catch (Exception e) {
            return new CustomResponse<>(ResponseStatus.INTERNAL_ERROR,
                    "Failed to update profile image: " + e.getMessage(), null);
        }
    }

    @Override
    public CustomResponse<String> updateProfileImage(String email, MultipartFile image) {
        try {
            if (email == null || email.trim().isEmpty())
                return new CustomResponse<>(ResponseStatus.BAD_REQUEST, "Email is required", null);

            if (image == null || image.isEmpty())
                return new CustomResponse<>(ResponseStatus.BAD_REQUEST, "Image file is required", null);

            // Fetch user
            User user = userRepo.findByEmail(email.toLowerCase().trim()).orElse(null);
            if (user == null)
                return new CustomResponse<>(ResponseStatus.NOT_FOUND, "User not found", null);

            if (!user.isActive())
                return new CustomResponse<>(ResponseStatus.UNAUTHORIZED, "User account is deactivated", null);

            // Update image: delete old one, upload new
            String oldImageUrl = user.getProfileImageUrl();
            String updatedImageUrl = imageHandler.updateImage(image, oldImageUrl, "profiles");

            // Update user data
            user.setProfileImageUrl(updatedImageUrl);
            user.setUpdatedAt(LocalDateTime.now());
            userRepo.save(user);

            return new CustomResponse<>(ResponseStatus.SUCCESS, "Profile image updated successfully", updatedImageUrl);
        } catch (IOException e) {
            return new CustomResponse<>(ResponseStatus.INTERNAL_ERROR,
                    "Image upload failed: " + e.getMessage(), null);
        } catch (Exception e) {
            return new CustomResponse<>(ResponseStatus.INTERNAL_ERROR,
                    "Failed to update profile image: " + e.getMessage(), null);
        }
    }

    @Override
    public CustomResponse<UserVerificationResponseDTO> forgetPassword(String email) {
        try {
            if (email == null || email.trim().isEmpty())
                return new CustomResponse<>(ResponseStatus.BAD_REQUEST, "Email is required", null);

            User user = userRepo.findByEmail(email).orElse(null);
            if (user == null)
                return new CustomResponse<>(ResponseStatus.NOT_FOUND, "User not found", null);

            // Check if user is active
            if (!user.isActive())
                return new CustomResponse<>(ResponseStatus.UNAUTHORIZED, "User account is deactivated", null);

            String fullName = user.getFirstName() + " " + user.getLastName();
            String otp = randomStringGenerator.generate(6);

            // Store OTP temporarily
            user.setToken(otp);
            user.setTokenExpirationAt(LocalDateTime.now().plusMinutes(15));
            User savedUser = userRepo.save(user);

            // Send verification email
            EmailService.Response<Void> emailResponse = emailService.sendPasswordResetEmail(
                    user.getEmail(), fullName, otp);

            UserVerificationResponseDTO responseDTO = convertToVerificationDTO(savedUser);
            if ("Success".equalsIgnoreCase(emailResponse.getStatus()))
                return new CustomResponse<>(ResponseStatus.SUCCESS,
                        "Password reset OTP sent successfully to your email", responseDTO);
            else
                return new CustomResponse<>(ResponseStatus.SUCCESS,
                        "OTP generated but failed to send email: " + emailResponse.getMessage(), responseDTO);
        } catch (Exception e) {
            return new CustomResponse<>(ResponseStatus.INTERNAL_ERROR,
                    "Failed to update profile image: " + e.getMessage(), null);
        }
    }

    @Override
    public CustomResponse<?> updatePassword(String email, UserUpdatePasswordDTO userUpdatePasswordDTO) {
        try {
            if (email == null || email.trim().isEmpty())
                return new CustomResponse<>(ResponseStatus.BAD_REQUEST, "Email is required", null);

            if (userUpdatePasswordDTO == null || userUpdatePasswordDTO.getOtp() == null || userUpdatePasswordDTO.getPassword() == null)
                return new CustomResponse<>(ResponseStatus.BAD_REQUEST, "OTP and new password are required", null);

            User user = userRepo.findByEmail(email).orElse(null);
            if (user == null)
                return new CustomResponse<>(ResponseStatus.NOT_FOUND, "User not found", null);

            // Check if user is active
            if (!user.isActive())
                return new CustomResponse<>(ResponseStatus.UNAUTHORIZED, "User account is deactivated", null);

            // Validate OTP
            if (user.getToken() == null || !user.getToken().equals(userUpdatePasswordDTO.getOtp()))
                return new CustomResponse<>(ResponseStatus.UNAUTHORIZED, "Invalid OTP", null);

            // Validate token expiration
            if (user.getTokenExpirationAt() == null || user.getTokenExpirationAt().isBefore(LocalDateTime.now()))
                return new CustomResponse<>(ResponseStatus.UNAUTHORIZED, "OTP has expired", null);

            // Update password
            user.setPassword(passwordEncoder.encode(userUpdatePasswordDTO.getPassword()));
            user.setToken(null); // clear OTP
            user.setTokenExpirationAt(null);
            user.setUpdatedAt(LocalDateTime.now());
            userRepo.save(user);

            return new CustomResponse<>(ResponseStatus.SUCCESS, "Password updated successfully", null);
        } catch (Exception e) {
            return new CustomResponse<>(ResponseStatus.INTERNAL_ERROR,
                    "Failed to update password: " + e.getMessage(), null);
        }
    }

    private UserResponseDTO convertToResponseDTO(User savedUser) {
        return this.modelMapper.map(savedUser, UserResponseDTO.class);
    }

    private UserVerificationResponseDTO convertToVerificationDTO(User user) {
        return this.modelMapper.map(user, UserVerificationResponseDTO.class);
    }
}
