package com.naukari.server.model.entity.user;

import com.naukari.server.model.enums.UserRole;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "Invalid email address")
    @NotEmpty(message = "Email address is required")
    @Size(max = 50, message = "Email must be lower than 50 characters")
    @Column(nullable = false, unique = true)
    private String email;

    @NotEmpty(message = "Password is required")
    @Size(min = 8, max = 255, message = "Password must be 8 - 255 characters")
    @Column(nullable = false)
    private String password;

    @NotEmpty(message = "Phone number required")
    @Size(min = 10, max = 10, message = "Phone number should be 10 digits")
    private String phone;

    @Size(min = 3, max = 3, message = "Country code should be 3 digits")
    @Column(name = "country_code", nullable = false)
    private String countryCode;

    @NotEmpty(message = "First name is required")
    @Size(max = 100, message = "First name must not exceed 100 characters")
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty(message = "Last name is required")
    @Size(max = 100, message = "Last name must not exceed 100 characters")
    @Column(name = "last_name")
    private String lastName;

    @Size(max = 100, message = "Middle name must not exceed 100 characters")
    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "token")
    private String token;

    @Size(max = 500)
    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false, length = 20)
    private UserRole role = UserRole.CANDIDATE;

    @Column(name = "is_active")
    private boolean isActive = true;

    @Column(name = "is_account_deleted")
    private boolean isAccountDeleted = false;

    @Column(name = "is_account_verified")
    private boolean isAccountVerified = false;

    @Column(name = "is_email_verified")
    private boolean isEmailVerified = false;

    @Column(name = "email_verified_at")
    private LocalDateTime emailVerifiedAt;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    @Column(name = "token_expiration_at")
    private LocalDateTime tokenExpirationAt;

    @Column(name = "account_deleted_at")
    private LocalDateTime accountDeletedAt;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}


