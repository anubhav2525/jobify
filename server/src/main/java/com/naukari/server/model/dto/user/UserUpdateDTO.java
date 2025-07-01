package com.naukari.server.model.dto.user;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {
    @NotEmpty
    @Min(message = "First name min 3 characters", value = 3)
    @Max(message = "First name max 50 characters", value = 50)
    private String firstName;

    @NotBlank
    @Min(message = "Last name min 3 characters", value = 3)
    @Max(message = "Last name max 50 characters", value = 50)
    private String lastName;

    private String middleName;

    @NotBlank
    @Size(min = 10, max = 10, message = "Phone number should be 10 digits")
    private String phone;
}
