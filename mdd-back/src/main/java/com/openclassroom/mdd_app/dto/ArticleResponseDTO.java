package com.openclassroom.mdd_app.dto;

import com.openclassroom.mdd_app.entities.Topic;

import java.util.List;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ArticleResponseDTO {
    private Long id;
	
	@NotNull(message = "Title cannot be NULL")
	@Size(max = 200)
	private String title;
	
	@NotNull(message = "Description cannot be NULL")
	@Size(max = 2500)
	private String description;
	
	private LocalDateTime date;

	@NotNull
	private UserDTO user;
	
	@NotNull
	private Topic topic;
	
	private List<CommentResponseDTO> comments;
}
