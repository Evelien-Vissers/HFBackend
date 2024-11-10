package com.novi.mappers;

import com.novi.dtos.UserRequestDTO;
import com.novi.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserDTOMapper {


    public User mapToModel(UserRequestDTO userDTO) {
        var result = new User();
        result.setEmail(userDTO.getUserName()); //Gebruik van email ipv username voor inloggen
        result.setPassword(userDTO.getPassword());
        return result;
    }

}
