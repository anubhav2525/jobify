package com.naukari.server.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResponseDTO {
    private String token;
    private String tokenType = "Bearer";
//    private UserResponseDTO user;

    public UserLoginResponseDTO(String token) {
        this.token = token;
//        this.user = user;
    }
}
