package com.openclassroom.mdd_app.payload.response;

import lombok.Data;

@Data
public class JwtResponse {
    private Long id;
    private String username;
    private String token;
    private String email;
    
    public JwtResponse(Long id, String username, String email, String accessToken) {
        this.token = accessToken;
        this.id = id;
        this.email = email;
        this.username = username;
    }
}
