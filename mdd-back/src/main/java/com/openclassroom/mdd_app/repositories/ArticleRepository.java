package com.openclassroom.mdd_app.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassroom.mdd_app.entities.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>{
    	List<Article> findByTopicIdIn(List<Long> topicIds);

}
