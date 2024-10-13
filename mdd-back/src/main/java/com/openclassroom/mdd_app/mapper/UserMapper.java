package com.openclassroom.mdd_app.mapper;

import com.openclassroom.mdd_app.dto.UserDTO;
import com.openclassroom.mdd_app.entities.User;

public class UserMapper {
    public static UserDTO toDto(User user) {
		
		if (user == null) {
			return null;
		}
		
		UserDTO userDto = new UserDTO();
		userDto.setId(user.getId());
		userDto.setName(user.getName());
		
		return userDto;
	}
}
