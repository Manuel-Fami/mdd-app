package com.openclassroom.mdd_app.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ArticleDTO {
    private Long id;
	
	@NotNull(message = "Title cannot be NULL")
	@Size(max = 200)
	private String title;
	
	@NotNull(message = "Description cannot be NULL")
	@Size(max = 2500)
	private String description;
	
	private LocalDateTime date;

	private Long userId;
	
	@NotNull(message = "Topic id cannot be NULL")
	private Long topicId;
}
