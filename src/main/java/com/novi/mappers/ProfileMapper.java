package com.novi.mappers;

import com.novi.dtos.ProfileInputDTO;
import com.novi.dtos.ProfileOutputDTO;
import com.novi.entities.Profile;
import org.springframework.stereotype.Component;



@Component
public class ProfileMapper {
    public ProfileOutputDTO toProfileOutputDTO(Profile profile) {
        ProfileOutputDTO dto = new ProfileOutputDTO();
        dto.setDateOfBirth(profile.getDateOfBirth());
        dto.setCity(profile.getCity());
        dto.setCountry(profile.getCountry());
        dto.setGender(profile.getGender());
        dto.setHealthChallenge(profile.getHealthChallenge());
        dto.setDiagnosisDate(profile.getDiagnosisDate());
        dto.setHospital(profile.getHospital());
        dto.setHealingChoice(profile.getHealingChoice());
        dto.setConnectionPreference(profile.getConnectionPreference());
        dto.setProfilePicUrl(profile.getProfilePicUrl());
        dto.setHealforceName(profile.getHealforceName());
        return dto;
    }

    public Profile toEntity(ProfileInputDTO profileInputDTO) {
        Profile profile = new Profile();
        profile.setDateOfBirth(profileInputDTO.getDateOfBirth());
        profile.setCity(profileInputDTO.getCity());
        profile.setCountry(profileInputDTO.getCountry());
        profile.setGender(profileInputDTO.getGender());
        profile.setHealthChallenge(profileInputDTO.getHealthChallenge());
        profile.setDiagnosisDate(profileInputDTO.getDiagnosisDate());
        profile.setHospital(profileInputDTO.getHospital());
        profile.setHealingChoice(profileInputDTO.getHealingChoice());
        profile.setConnectionPreference(profileInputDTO.getConnectionPreference());
        profile.setHealforceName(profileInputDTO.getHealforceName());
        return profile;
    }

}
