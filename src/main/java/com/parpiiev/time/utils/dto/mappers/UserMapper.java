package com.parpiiev.time.utils.dto.mappers;

import com.parpiiev.time.model.User;
import com.parpiiev.time.utils.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements DtoMapper<UserDTO, User>{
    @Override
    public UserDTO mapToDto(User user) {
        UserDTO dto = new UserDTO();
        dto.setUser_id(user.getId());
        dto.setName(user.getUsername());
        dto.setLogin(user.getLogin());
        dto.setPassword(user.getPassword());
        dto.setRole(user.getRole());
        return dto;
    }

    @Override
    public User mapFromDto(UserDTO userDto) {
        User user = new User();
        user.setUsername(userDto.getName());
        user.setLogin(userDto.getLogin());
        user.setPassword(userDto.getPassword());
        user.setRole(userDto.getRole());
        return user;
    }
}
