package com.openclassroom.mdd_app.mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.openclassroom.mdd_app.dto.ArticleDTO;
import com.openclassroom.mdd_app.dto.ArticleResponseDTO;
import com.openclassroom.mdd_app.dto.CommentResponseDTO;
import com.openclassroom.mdd_app.dto.UserDTO;
import com.openclassroom.mdd_app.entities.Article;
import com.openclassroom.mdd_app.entities.Topic;
import com.openclassroom.mdd_app.entities.User;

public class ArticleMapper {
    public static ArticleDTO toDto(Article article) {
		
        if (article == null) {
            return null;
        }

        ArticleDTO articleDto = new ArticleDTO();
        articleDto.setId(article.getId());
        articleDto.setTitle(article.getTitle());
        articleDto.setDescription(article.getDescription());
        articleDto.setDate(article.getDate());
        articleDto.setUserId(article.getUser().getId());
        articleDto.setTopicId(article.getTopic().getId());

        return articleDto;
    }

    public static Article toEntity(ArticleDTO articleDto, User currentUser, Topic topic) {
    	
        if (articleDto == null || currentUser == null || topic == null) {
             return null;
        }

        Article article = new Article();
        article.setId(articleDto.getId());
        article.setTitle(articleDto.getTitle());
        article.setDescription(articleDto.getDescription());
        article.setUser(currentUser);
        article.setTopic(topic);
        
        return article;
    }

    public static List<ArticleDTO> toDtoList(List<Article> articles) {
        return articles.stream().map(ArticleMapper::toDto).collect(Collectors.toList());
    }

    public static ArticleResponseDTO toResponseDto(Article article, Topic topic, UserDTO user, List<CommentResponseDTO> comments) {
        ArticleResponseDTO articleResponseDto = new ArticleResponseDTO();
        articleResponseDto.setId(article.getId());
        articleResponseDto.setTitle(article.getTitle());
        articleResponseDto.setDescription(article.getDescription());
        articleResponseDto.setDate(article.getDate());
        articleResponseDto.setTopic(topic);
        articleResponseDto.setUser(user);
        articleResponseDto.setComments(comments != null ? comments : Collections.emptyList());
        
        return articleResponseDto;
    }
}
