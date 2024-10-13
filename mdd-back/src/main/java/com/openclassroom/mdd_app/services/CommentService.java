package com.openclassroom.mdd_app.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.openclassroom.mdd_app.entities.Comment;
import com.openclassroom.mdd_app.repositories.ArticleRepository;
import com.openclassroom.mdd_app.repositories.CommentRepository;
import com.openclassroom.mdd_app.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CommentService {
    public CommentRepository commentRepository;
	public UserRepository userRepository;
	public ArticleRepository articleRepository;
	
	public CommentService(CommentRepository commentRepository, UserRepository userRepository, ArticleRepository articleRepository) {
		this.commentRepository = commentRepository;
		this.userRepository = userRepository;
		this.articleRepository = articleRepository;
	}

    public void create(Comment comment) {

		commentRepository.save(comment);	
	}
	
	public List<Comment> getAll(Long articleId) {
			
		articleRepository.findById(articleId)
					.orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + articleId));
			
		List<Comment> comments = commentRepository.findByArticleId(articleId);
		
		return comments;
	}
}
