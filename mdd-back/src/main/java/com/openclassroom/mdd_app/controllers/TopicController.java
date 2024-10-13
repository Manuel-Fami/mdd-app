package com.openclassroom.mdd_app.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.openclassroom.mdd_app.dto.TopicDTO;
import com.openclassroom.mdd_app.entities.Topic;
import com.openclassroom.mdd_app.mapper.TopicMapper;
import com.openclassroom.mdd_app.services.TopicService;

public class TopicController {
    
	public TopicService service;
	
	public TopicController(TopicService service) {
		this.service= service;	
	}

    public ResponseEntity<List<TopicDTO>> getAll() {

		List<Topic> list = service.getAll();
		List<TopicDTO> dtoList = TopicMapper.toDtoList(list);

		return ResponseEntity.ok().body(dtoList);
		
	}
}
