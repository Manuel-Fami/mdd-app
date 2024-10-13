package com.openclassroom.mdd_app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.openclassroom.mdd_app.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{
    public List<Comment> findByArticleId(Long articleId);
} 