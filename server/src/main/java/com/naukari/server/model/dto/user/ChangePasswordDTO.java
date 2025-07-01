package com.naukari.server.model.dto.user;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordDTO {
    @NotEmpty(message = "Current password is required")
    @Min(value = 8, message = "Current password must be min 8 characters")
    @Max(value = 50, message = "Current password must be max 50 characters")
    private String currentPassword;

    @NotEmpty(message = "New password is required")
    @Min(value = 8, message = "New password must be min 8 characters")
    @Max(value = 50, message = "New password must be max 50 characters")
    private String newPassword;
}
