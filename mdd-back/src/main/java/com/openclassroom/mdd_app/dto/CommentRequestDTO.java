package com.openclassroom.mdd_app.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentRequestDTO {
    @Size(max=2500)
	@NotNull
	private String content;
}
