package com.novi.mappers;

import com.novi.dtos.UserInputDTO;
import com.novi.dtos.UserOutputDTO;
import com.novi.entities.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public static UserOutputDTO toUserOutputDTO(User user) {
        UserOutputDTO dto = new UserOutputDTO();
        dto.setId(user.getId());
        dto.setProfileId(user.getProfile() != null ? user.getProfile().getId() : null);
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setAcceptedPrivacyStatementUserAgreement(user.getAcceptedPrivacyStatementUserAgreement());
        dto.setRegistrationDate(user.getRegistrationDate());
        dto.setLastLogin(user.getLastLogin());
        return dto;
    }

    public static User toUser(UserInputDTO userInputDTO) {
        User user = new User();
        user.setFirstName(userInputDTO.getFirstName());
        user.setLastName(userInputDTO.getLastName());
        user.setEmail(userInputDTO.getEmail());
        user.setPassword(userInputDTO.getPassword());
        user.setAcceptedPrivacyStatementUserAgreement(userInputDTO.getAcceptedPrivacyStatementUserAgreement());
        return user;
    }

    public static List<UserOutputDTO> toUserOutputDTOList(List<User> users) {
        return users.stream()
                .map(UserMapper::toUserOutputDTO)
                .collect(Collectors.toList());
    }
}
