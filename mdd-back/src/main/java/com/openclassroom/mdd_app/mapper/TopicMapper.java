package com.openclassroom.mdd_app.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.openclassroom.mdd_app.dto.TopicDTO;
import com.openclassroom.mdd_app.entities.Topic;

public class TopicMapper {
    public static TopicDTO toDto(Topic topic) {
		
        if (topic == null) {
            return null;
        }

        TopicDTO topicDto = new TopicDTO();
        topicDto.setId(topic.getId());
        topicDto.setTitle(topic.getTitle());
        topicDto.setDescription(topic.getDescription());

        return topicDto;
    }

    public static List<TopicDTO> toDtoList(List<Topic> topics) {
        return topics.stream().map(TopicMapper::toDto).collect(Collectors.toList());
    }
}
