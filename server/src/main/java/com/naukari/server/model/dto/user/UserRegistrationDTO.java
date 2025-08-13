package com.naukari.server.model.dto.user;

import com.naukari.server.model.enums.UserRole;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDTO {
    @Email(message = "Invalid email address")
    @NotEmpty(message = "Email address is required")
    @Size(max = 50, message = "Email must be lower than 50 characters")
    private String email;

    @NotEmpty(message = "Password is required")
    @Size(min = 8, max = 255, message = "Password must be 8 - 255 characters")
    private String password;

    @NotEmpty(message = "Phone number required")
    @Size(min = 10, max = 10, message = "Phone number should be 10 digits")
    private String phone;

    @NotEmpty(message = "Country required")
    private String country;

    @Size(min = 2, max = 3, message = "Incorrect Country code")
    private String countryCode;

    @NotBlank(message = "First name is required")
    @Size(max = 100, message = "First name must not exceed 100 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 100, message = "Last name must not exceed 100 characters")
    private String lastName;

    @Size(max = 100, message = "Middle name must not exceed 100 characters")
    private String middleName;

//    private MultipartFile profileImage;

    private UserRole role = UserRole.CANDIDATE;

    public void setRole(UserRole userRole) {
        this.role = userRole;
    }
}