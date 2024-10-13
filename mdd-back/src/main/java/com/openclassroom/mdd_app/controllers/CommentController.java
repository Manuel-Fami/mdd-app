package com.openclassroom.mdd_app.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassroom.mdd_app.dto.CommentRequestDTO;
import com.openclassroom.mdd_app.entities.Article;
import com.openclassroom.mdd_app.entities.Comment;
import com.openclassroom.mdd_app.entities.User;
import com.openclassroom.mdd_app.mapper.CommentMapper;
import com.openclassroom.mdd_app.payload.response.MessageResponseHandler;
import com.openclassroom.mdd_app.services.ArticleService;
import com.openclassroom.mdd_app.services.CommentService;
import com.openclassroom.mdd_app.services.UserService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/comment")
public class CommentController {
    public CommentService commentService;
	public UserService userService;
	public ArticleService articleService;
	
	public CommentController(CommentService commentService, UserService userService, ArticleService articleService) {
		this.commentService = commentService;
		this.userService = userService;
		this.articleService = articleService;
	}

    @PostMapping("/{articleId}/create")
    public ResponseEntity<MessageResponseHandler> create(@Valid @RequestBody CommentRequestDTO request, @PathVariable Long articleId) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails currentUser = (UserDetails) authentication.getPrincipal();
        String mail = currentUser.getUsername();
        
        User user = userService.getUser(mail);
        Article article = articleService.getById(articleId);
        
        Comment comment = CommentMapper.toEntity(request, user, article);
       
        commentService.create(comment);
		
        return ResponseEntity.ok(new MessageResponseHandler("Comment successfully added"));		
	}
}
