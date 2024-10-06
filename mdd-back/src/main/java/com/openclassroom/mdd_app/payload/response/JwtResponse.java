package com.openclassroom.mdd_app.payload.response;

import lombok.Data;

@Data
public class JwtResponse {
    private Long id;
    private String username;
    private String token;
    private String mail;
    
    public JwtResponse(Long id, String username, String mail, String accessToken) {
        this.token = accessToken;
        this.id = id;
        this.mail = mail;
        this.username = username;
    }
}
