package com.openclassroom.mdd_app.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassroom.mdd_app.dto.ArticleDTO;
import com.openclassroom.mdd_app.dto.ArticleResponseDTO;
import com.openclassroom.mdd_app.dto.CommentResponseDTO;
import com.openclassroom.mdd_app.dto.UserDTO;
import com.openclassroom.mdd_app.entities.Article;
import com.openclassroom.mdd_app.entities.Comment;
import com.openclassroom.mdd_app.entities.Topic;
import com.openclassroom.mdd_app.entities.User;
import com.openclassroom.mdd_app.mapper.ArticleMapper;
import com.openclassroom.mdd_app.mapper.CommentMapper;
import com.openclassroom.mdd_app.mapper.UserMapper;
import com.openclassroom.mdd_app.payload.response.MessageResponseHandler;
import com.openclassroom.mdd_app.services.ArticleService;
import com.openclassroom.mdd_app.services.CommentService;
import com.openclassroom.mdd_app.services.TopicService;
import com.openclassroom.mdd_app.services.UserService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/articles")
public class ArticleController {
    public UserService userService;
	public ArticleService articleService;
	public CommentService commentService;
	public TopicService topicService;
	
	public ArticleController(ArticleService articleService, CommentService commentService, TopicService topicService, UserService userService) {
		this.articleService = articleService;
		this.commentService = commentService;
		this.topicService = topicService;
		this.userService = userService;
	}

    @PostMapping("/create")
    public ResponseEntity<MessageResponseHandler> create(@Valid @RequestBody ArticleDTO request) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails currentUser = (UserDetails) authentication.getPrincipal();
        String mail = currentUser.getUsername();
        
        User user = userService.getUser(mail);
        
        Long topicId = request.getTopicId();
        Topic topic = topicService.getById(topicId);
        Article article = ArticleMapper.toEntity(request, user, topic);
              
        articleService.create(article);
		
        return ResponseEntity.ok(new MessageResponseHandler("Article créé avec succès !"));		
	}

    @GetMapping("/all")
    public ResponseEntity<List<ArticleResponseDTO>> getAll() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails currentUser = (UserDetails) authentication.getPrincipal();
        String username = currentUser.getUsername();
        
        List<Article> articles = articleService.getAll(username);
        
        List<ArticleResponseDTO> articleResponseDtos = articles.stream()
                .map((Article article) -> {
                    Topic topic = article.getTopic();
                    User user = article.getUser();
                    UserDTO userDto = UserMapper.toDto(user);
                    return ArticleMapper.toResponseDto(article, topic, userDto, null);
                })
                .collect(Collectors.toList());
        
        return ResponseEntity.ok().body(articleResponseDtos);
	}

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponseDTO> getById(@PathVariable Long id) {
				
		Article article = articleService.getById(id);
		Topic topic = article.getTopic();
		User user = article.getUser();
		UserDTO userDto = UserMapper.toDto(user);
		
		List<Comment> comments = commentService.getAll(id);
		List<CommentResponseDTO> commentsDtoList = CommentMapper.toDtoList(comments);
		
		ArticleResponseDTO articleResponseDto = ArticleMapper.toResponseDto(article, topic, userDto, commentsDtoList);
		
		return ResponseEntity.ok().body(articleResponseDto);
	}
}
