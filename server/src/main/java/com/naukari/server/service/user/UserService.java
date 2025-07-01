package com.naukari.server.service.user;

import com.naukari.server.model.dto.user.*;
import com.naukari.server.utils.CustomResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    // create a new user
    CustomResponse<UserVerificationResponseDTO> createUser(UserRegistrationDTO userRegistration);

    // get user by user id
    CustomResponse<UserResponseDTO> getUserById(Long id);

    // get user by email
    CustomResponse<UserResponseDTO> getUserByEmail(String email);

    // get all users
    CustomResponse<List<UserResponseDTO>> getAllUsers();

    // === UPDATE ===
    CustomResponse<UserResponseDTO> updateUserByEmail(String email, UserUpdateDTO updateDTO);

    CustomResponse<UserResponseDTO> changePassword(String email, ChangePasswordDTO passwordDTO);

    CustomResponse<UserVerificationResponseDTO> verifyEmail(String email, UserVerificationDTO userVerificationDTO);

    // === DELETE / DEACTIVATE ===
    CustomResponse<String> deactivateUser(String email);   // Soft delete

    CustomResponse<String> deleteUser(String email);       // Hard delete (Admin only)

    // === AUTHENTICATION ===
    CustomResponse<UserLoginResponseDTO> loginUser(UserLoginDTO userLoginDTO);

    // === PROFILE / ACCOUNT ===
    CustomResponse<Boolean> isEmailVerified(String email);

    CustomResponse<String> uploadProfileImage(String email, MultipartFile image);

    CustomResponse<String> updateProfileImage(String email, MultipartFile image);

    CustomResponse<UserVerificationResponseDTO> forgetPassword(String email);

    CustomResponse<?> updatePassword(String email, UserUpdatePasswordDTO userUpdatePasswordDTO);
}
