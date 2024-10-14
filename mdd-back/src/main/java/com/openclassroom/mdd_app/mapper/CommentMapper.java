package com.openclassroom.mdd_app.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.openclassroom.mdd_app.dto.CommentRequestDTO;
import com.openclassroom.mdd_app.dto.CommentResponseDTO;
import com.openclassroom.mdd_app.entities.Article;
import com.openclassroom.mdd_app.entities.Comment;
import com.openclassroom.mdd_app.entities.User;

public class CommentMapper {
    public static CommentResponseDTO toDto(Comment comment) {
        if (comment == null) {
            return null;
        }
        
        CommentResponseDTO dto = new CommentResponseDTO();
        dto.setContent(comment.getContent());
        dto.setDate(comment.getDate());
        dto.setUser(UserMapper.toDto(comment.getUser()));
        
        return dto;
    }

    public static List<CommentResponseDTO> toDtoList(List<Comment> comments) {
        return comments.stream()
                .map(CommentMapper::toDto)
                .collect(Collectors.toList());
    }
    
    
    public static Comment toEntity(CommentRequestDTO commentDto, User user, Article article) {
    	
    	if (commentDto == null || user == null || article == null) {
            return null;
        }
    	
    	Comment comment = new Comment();
    	comment.setContent(commentDto.getContent());
    	comment.setUser(user);
    	comment.setArticle(article);
    	
    	return comment;	
    }
}
