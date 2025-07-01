package com.naukari.server.model.dto.user;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@Embeddable
@AllArgsConstructor
public class UserLoginDTO {

    @Email(message = "Invalid email address")
    @NotEmpty(message = "Email address is required")
    @Size(max = 50, message = "Email must be lower than 50 characters")
    private String email;

    @NotEmpty(message = "Password is required")
    @Size(min = 8, max = 255, message = "Password must be 8 - 255 characters")
    private String password;
}

