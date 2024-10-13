package com.openclassroom.mdd_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.openclassroom.mdd_app.entities.Topic;

public interface TopicRepository extends JpaRepository<Topic, Long>{  
} 
