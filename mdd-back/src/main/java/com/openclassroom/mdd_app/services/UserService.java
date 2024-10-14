package com.openclassroom.mdd_app.services;

import org.springframework.stereotype.Service;

import com.openclassroom.mdd_app.entities.User;
import com.openclassroom.mdd_app.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {
    public UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public User getUser(String mail) {
		
		User user = userRepository.findByEmail(mail)
                .orElseThrow(() -> new EntityNotFoundException("User not found with mail: " + mail));
		
		return user;
	}
}
