package com.openclassroom.mdd_app.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentResponseDTO {
    @Size(max=2500)
	private String content;
	
	private UserDTO user;
	
	private LocalDateTime date;
	
}
