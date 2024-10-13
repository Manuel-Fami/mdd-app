package com.openclassroom.mdd_app.entities;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Username cannot be null")
    @Column(nullable = false)
    private String name;

    @Email(message = "Please enter a valid email")
    @NotNull(message = "Email cannot be null")
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull(message = "Password cannot be null")
    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Subscription> subscriptions;
   
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
 
    @Override
    public String getPassword() {
        return this.password;
    }
 
    @Override
    public String getUsername() {
        return this.email;
    }
}