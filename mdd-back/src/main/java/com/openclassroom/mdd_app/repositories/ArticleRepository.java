package com.openclassroom.mdd_app.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassroom.mdd_app.entities.Article;

public interface ArticleRepository extends JpaRepository<Article, Long>{
    	List<Article> findByTopicIdIn(List<Long> topicIds);

}
