package com.openclassroom.mdd_app.services;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import com.openclassroom.mdd_app.entities.User;
import com.openclassroom.mdd_app.exceptions.CustomException;
import com.openclassroom.mdd_app.repositories.UserRepository;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    //Constructeur (meme nom de la classe)
    public AuthService(
		        UserRepository userRepository,
                AuthenticationManager authenticationManager
		    ) 
            {
		        this.userRepository = userRepository;
                this.authenticationManager = authenticationManager;

	 		}

    public void register(User user) {

        // Vérification si email existe
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new CustomException("Email already in use", HttpStatus.CONFLICT);
        }
        
        userRepository.save(user);
    }

    public User login (User user) {

        // Authentification Spring Security
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

        return userRepository.findByEmail(user.getEmail())
        .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));			 
    }

     public User updateCredentials(User updatedUser) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			 
	 	if (authentication == null || !authentication.isAuthenticated()) {
            throw new CustomException("User not authenticated", HttpStatus.BAD_REQUEST);
	 	}
			 
	 	String email = authentication.getName();
	 	User currentUser = userRepository.findByEmail(email)
                    .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
			 
	 	/* User has updated his email : we have to check if the new email is already in use for another user */
	 	if (!currentUser.getEmail().equals(updatedUser.getEmail())) {
               if (userRepository.existsByEmail(updatedUser.getEmail())) {
                  	 	throw new CustomException("Email already in use", HttpStatus.CONFLICT);
	           }
		}
			 
		currentUser.setName(updatedUser.getName());
		currentUser.setEmail(updatedUser.getEmail());
		currentUser.setPassword(updatedUser.getPassword());
	         
		return userRepository.save(currentUser); 
	 }
}
