package com.openclassroom.mdd_app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassroom.mdd_app.entities.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
    public List<Comment> findByArticleId(Long articleId);
} 