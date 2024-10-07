package com.novi.mappers;

import com.novi.dtos.UserInputDTO;
import com.novi.dtos.UserOutputDTO;
import com.novi.entities.User;

public class UserMapper {

    // Map een User naar een UserOutputDTO
    public static UserOutputDTO toUserOutputDTO(User user) {
        UserOutputDTO dto = new UserOutputDTO();
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
       // dto.setAcceptedPrivacyPolicyUserAgreement(user.AcceptedPrivacyStatementUserAgreement());
        dto.setVerifiedEmail(user.getVerifiedEmail());
        dto.setRegistrationDate(user.getRegistrationDate());
        dto.setLastLogin(user.getLastLogin());
        dto.setHasCompletedQuestionnaire(user.getHasCompletedQuestionnaire());
        return dto;
    }

    // Map een UserInputDTO naar een User
    public static User toUser(UserInputDTO userInputDTO) {
        User user = new User();
        user.setFirstName(userInputDTO.getFirstName());
        user.setLastName(userInputDTO.getLastName());
        user.setEmail(userInputDTO.getEmail());
        user.setPassword(userInputDTO.getPassword());  // Zorg voor wachtwoordversleuteling in een echte omgeving
        //user.setAcceptedPrivacyStatementUserAgreement(userInputDTO.isAcceptedPrivacyStatementUserAgreement());
        return user;
    }
}
