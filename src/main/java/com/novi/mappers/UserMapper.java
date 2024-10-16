package com.novi.mappers;

import com.novi.dtos.UserInputDTO;
import com.novi.dtos.UserOutputDTO;
import com.novi.entities.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    // Map een User naar een UserOutputDTO
    public static UserOutputDTO toUserOutputDTO(User user) {
        UserOutputDTO dto = new UserOutputDTO();
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        //dto.setAcceptedPrivacyPolicyUserAgreement(user.AcceptedPrivacyStatementUserAgreement());
        dto.setVerifiedEmail(user.getVerifiedEmail());
        dto.setRegistrationDate(user.getRegistrationDate());
        dto.setLastLogin(user.getLastLogin());
        dto.setHasCompletedQuestionnaire(user.getHasCompletedQuestionnaire());
        return dto;
    }

    // Map een UserInputDTO naar een User
    public static User toUser(UserInputDTO userInputDTO) {
        User user = new User(-1L);
        user.setFirstName(userInputDTO.getFirstName());
        user.setLastName(userInputDTO.getLastName());
        user.setEmail(userInputDTO.getEmail());
        user.setPassword(userInputDTO.getPassword());  // Zorg voor wachtwoordversleuteling in een echte omgeving
        //user.setAcceptedPrivacyStatementUserAgreement(userInputDTO.isAcceptedPrivacyStatementUserAgreement());
        return user;
    }

    // Map een lijst van User entities naar UserOutputDTO's
    public static List<UserOutputDTO> toUserOutputDTOList(List<User> users) {
        return users.stream()
                .map(UserMapper::toUserOutputDTO)
                .collect(Collectors.toList());
    }
}
