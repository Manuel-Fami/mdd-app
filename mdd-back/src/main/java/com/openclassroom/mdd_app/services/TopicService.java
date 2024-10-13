package com.openclassroom.mdd_app.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.openclassroom.mdd_app.entities.Topic;
import com.openclassroom.mdd_app.repositories.TopicRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TopicService {
    public TopicRepository repository;
	
	public TopicService(TopicRepository repository) {
		this.repository = repository;
	}
	
	
	public List<Topic> getAll() {
		
		List<Topic> list = this.repository.findAll();
		
		return list;
	}
	
	public Topic getById(Long id) {
		
		Topic topic = repository.findById(id)
			 	.orElseThrow(() -> new EntityNotFoundException("Topic not found with id: " + id));
		
		return topic;
	}
}
