package com.openclassroom.mdd_app.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassroom.mdd_app.dto.UserDTO;
import com.openclassroom.mdd_app.entities.User;
import com.openclassroom.mdd_app.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/user")
public class UserController {
    public UserRepository userRepository;
	
	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
		
			User user = userRepository.findById(id)
					.orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
			UserDTO userDto = new UserDTO();
			userDto.setId(user.getId());
			userDto.setName(user.getName());
			
			return ResponseEntity.ok().body(userDto);
	}
}
