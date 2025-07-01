package com.naukari.server.model.dto.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVerificationDTO {
    @NotEmpty(message = "Token is required")
    @Size(min = 6, max = 6, message = "Token must be 6 digits")
    private String token;
}
