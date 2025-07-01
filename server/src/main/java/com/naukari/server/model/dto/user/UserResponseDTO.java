package com.naukari.server.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String middleName;
    private String profileImageUrl;
    private String role;
    private String phone;
    private String countryCode;
    private boolean isActive;
    private boolean isAccountVerified;
    private LocalDateTime emailVerifiedAt;
    private LocalDateTime lastLoginAt;
}
