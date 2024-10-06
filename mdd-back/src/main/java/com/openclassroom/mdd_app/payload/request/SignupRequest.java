package com.openclassroom.mdd_app.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupRequest {
    @NotBlank(message = "email is required")
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank(message = "name is required")
    @Size(max = 30)
    private String name;

    @NotBlank(message = "password is required")
    @Size(min = 8, max = 40)
    private String password;
}
