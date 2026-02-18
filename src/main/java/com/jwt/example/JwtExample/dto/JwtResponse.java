package com.jwt.example.JwtExample.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtResponse {

    private String token;
    private String type;
    private String username;
    private String email;
    private Long expiresIn;

    public JwtResponse(String token, String username, String email) {
        this.token = token;
        this.type = "Bearer";
        this.username = username;
        this.email = email;
    }
}

