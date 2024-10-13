package com.openclassroom.mdd_app.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassroom.mdd_app.entities.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long>{
    List<Subscription> findByUserId(Long userId);
	boolean existsByUserIdAndTopicId(Long userId, Long topicId);
}
