package com.naukari.server.model.dto.user;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdatePasswordDTO {
    @NotEmpty(message = "OTP is required")
    @Size(min = 6, max = 6, message = "OTP must be 6 characters")
    private String otp;

    @NotEmpty(message = "New password is required")
    @Min(value = 8, message = "New password must be min 8 characters")
    @Max(value = 50, message = "New password must be max 50 characters")
    private String password;
}
