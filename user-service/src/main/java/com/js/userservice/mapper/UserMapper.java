package com.js.userservice.mapper;

import com.js.userservice.dto.UserDTO;
import com.js.userservice.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public User userDTOToEntity(UserDTO userDTO) {
        return new User(userDTO.id(), userDTO.name(), userDTO.balance());
    }

    public UserDTO userEntityToDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getBalance());
    }

}
