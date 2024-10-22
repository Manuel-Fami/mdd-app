package com.openclassroom.mdd_app.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.openclassroom.mdd_app.entities.Article;
import com.openclassroom.mdd_app.entities.Subscription;
import com.openclassroom.mdd_app.entities.User;
import com.openclassroom.mdd_app.repositories.ArticleRepository;
import com.openclassroom.mdd_app.repositories.SubscriptionRepository;
import com.openclassroom.mdd_app.repositories.TopicRepository;
import com.openclassroom.mdd_app.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ArticleService {
    public ArticleRepository articleRepository;
	public UserRepository userRepository;
	public TopicRepository topicRepository;
	public SubscriptionRepository subscriptionRepository;
	private static final Logger logger = LoggerFactory.getLogger(ArticleService.class);


    public ArticleService(ArticleRepository articleRepository, UserRepository userRepository, 
			TopicRepository topicRepository,SubscriptionRepository subscriptionRepository) {
		this.articleRepository = articleRepository;
		this.userRepository = userRepository;
		this.topicRepository = topicRepository;
		this.subscriptionRepository = subscriptionRepository;
		// this.commentService = commentService;
	}
    
    public void create(Article article) { 
        articleRepository.save(article);
    }

    public List<Article> getAll(String username) {
			
		User currentUser = userRepository.findByEmail(username)
	                .orElseThrow(() -> new EntityNotFoundException("User not found with mail: " + username));
			
		Long userId = currentUser.getId();
			
		List<Subscription> subscriptions = subscriptionRepository.findByUserId(userId);
		logger.info("Abonnements trouvés pour l'utilisateur {}: {}", username, subscriptions);


	    List<Long> topicIds = subscriptions.stream()
	                 .map(subscription -> subscription.getTopic().getId())
	                 .collect(Collectors.toList());

		logger.info("IDs des topics abonnés: {}", topicIds);


	    List<Article> articles = articleRepository.findByTopicIdIn(topicIds);
		logger.info("Articles récupérés: {}", articles);

	         
	    return articles;		
	}

    public Article getById(Long id) {
			
		Article post = articleRepository.findById(id)
					.orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + id));
			
		return post;	
	}
}
