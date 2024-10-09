package com.openclassroom.mdd_app.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassroom.mdd_app.entities.User;
import com.openclassroom.mdd_app.payload.request.LoginRequest;
import com.openclassroom.mdd_app.payload.request.SignupRequest;
import com.openclassroom.mdd_app.payload.response.JwtResponse;
import com.openclassroom.mdd_app.payload.response.MessageResponseHandler;
import com.openclassroom.mdd_app.services.AuthService;
import com.openclassroom.mdd_app.services.JwtService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthController {
    private final JwtService jwtService;
	private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

	
	public AuthController(JwtService jwtService, AuthService authService, PasswordEncoder passwordEncoder) {
		this.jwtService = jwtService;
		this.authService = authService;
        this.passwordEncoder = passwordEncoder;

	}

    @PostMapping("/register")
    public ResponseEntity<MessageResponseHandler> register(@Valid @RequestBody SignupRequest signupRequest) {
        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        authService.register(user);

        return ResponseEntity.ok(new MessageResponseHandler("User registered successfully!"));
	}

	@PostMapping("/login")
    @CrossOrigin(origins = "*")
	public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        User user = new User();
        user.setEmail(loginRequest.getEmail());
        user.setPassword(loginRequest.getPassword());

        User authenticatedUser = authService.login(user);	
		String jwtToken = jwtService.generateToken(authenticatedUser);

        return ResponseEntity.ok(new JwtResponse(
				 authenticatedUser.getId(),
				 authenticatedUser.getName(),
				 authenticatedUser.getEmail(),
				 jwtToken
		));

	}

    @PutMapping("/update")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<JwtResponse> updateCredentials(@Valid @RequestBody SignupRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        
        User updatedUser = authService.updateCredentials(user);
        String jwtToken = jwtService.generateToken(updatedUser);
        
        return ResponseEntity.ok(new JwtResponse(
                updatedUser.getId(),
                updatedUser.getName(),
                updatedUser.getEmail(),
                jwtToken
       )); 
    }
}
