package com.openclassroom.mdd_app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserDTO {

    private Long id;
    @NotBlank(message = "name is required")
	private String name;
    @NotBlank(message = "email is required")
    @Email(message="Invalid email format")
    private String email;
    @NotBlank(message = "password is required")
    private String password;
	
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
		this.name = name;
	}

    @NotNull(message = "email cannot be NULL")
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
	    this.email = email;
	}



    @NotNull(message = "password cannot be NULL")
    public String getPassword() {
        return this.password;
    }

}
