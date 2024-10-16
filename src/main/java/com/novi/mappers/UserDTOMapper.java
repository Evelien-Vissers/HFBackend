package com.novi.mappers;

//Mapper voor User Security


import com.novi.dtos.UserRequestDTO;
import com.novi.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserDTOMapper {



    public User mapToModel(UserRequestDTO userDTO) {
        var result = new User(-1L);
        result.setUserName(userDTO.getUserName());
        result.setPassword(userDTO.getPassword());
        return result;
    }

}
