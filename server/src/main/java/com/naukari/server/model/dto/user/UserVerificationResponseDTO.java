package com.naukari.server.model.dto.user;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVerificationResponseDTO {
    private Long id;
    private String email;
    private String firstName;
    private String role;
    private String token;
    private LocalDateTime createdAt;
}
