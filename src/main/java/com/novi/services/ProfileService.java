package com.novi.services;
//Dit is de service die de businesslogica afhandelt voor het ophalen en bijwerken van profielinformatie

import com.novi.dtos.ProfileInputDTO;
import com.novi.dtos.ProfileMatchingOutputDTO;
import com.novi.dtos.ProfileOutputDTO;
import com.novi.entities.Profile;
import com.novi.entities.User;
import com.novi.repositories.ProfileRepository;
import com.novi.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    public ProfileService(ProfileRepository profileRepository, UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    // Sla een nieuw profiel op met de bijbehorende ID
    @Transactional
    public void saveProfile(Long ID, ProfileInputDTO profileInputDTO) {
        //Zoek de gebruiker obv ID
        User user = userRepository.findById(ID)
                .orElseThrow(() -> new RuntimeException("User not found"));

        //Maak nieuw profiel aan en koppel het aan de gebruiker
        Profile profile = new Profile();
        profile.setDateOfBirth(profileInputDTO.getDateOfBirth());  // i. LocalDate dateOfBirth
        profile.setLocation(profileInputDTO.getLocation());        // ii. String location
        profile.setGender(profileInputDTO.getGender());            // iii. String gender
        profile.setHealthChallenge(profileInputDTO.getHealthChallenge()); // iv. String healthChallenge
        profile.setDiagnosisDate(profileInputDTO.getDiagnosisDate());     // v. YearMonth diagnosisDate
        profile.setHealingChoice(profileInputDTO.getHealingChoice()); // vi. String healingPreference
        profile.setConnectionPreference(profileInputDTO.getConnectionPreference()); // vii. String connectionPreference
        profile.setProfilePic(profileInputDTO.getProfilePic());    // viii. String profilePic
        profile.setHealforceName(profileInputDTO.getHealforceName());

        //Koppel het profiel aan de gebruiker (1-op-1 relatie User en Profile)
        profile.setUser(user);

        //Het profiel wordt opgeslagen zonder profileID (deze wordt gegenereerd na de eerste opslag)
        profileRepository.save(profile);
    }

    //Genereer en sla een profileID op
    public Long generateProfileID(Long ID) {
        User user = userRepository.findById(ID)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Profile profile = profileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Profile not found for user"));

        return profile.getId(); //De profileID is het ID van het profiel dat aan de gebruiker (ID) is gekoppeld.
    }

    //Haal een profiel op adhv profileID
    public ProfileOutputDTO getProfileByProfileID(Long profileID) {
        Optional<Profile> profile = profileRepository.findById(profileID);
        if (profile.isEmpty()) {
            throw new RuntimeException("Profile not found");
        }
        ProfileOutputDTO profileOutputDTO = new ProfileOutputDTO();
        profileOutputDTO.setDateOfBirth(profile.get().getDateOfBirth());  // i. LocalDate dateOfBirth
        profileOutputDTO.setLocation(profile.get().getLocation());        // ii. String location
        profileOutputDTO.setGender(profile.get().getGender());            // iii. String gender
        profileOutputDTO.setHealthChallenge(profile.get().getHealthChallenge()); // iv. String healthChallenge
        profileOutputDTO.setDiagnosisDate(profile.get().getDiagnosisDate());     // v. YearMonth diagnosisDate
        profileOutputDTO.setHealingChoice(profile.get().getHealingChoice()); // vi. String healingPreference
        profileOutputDTO.setConnectionPreference(profile.get().getConnectionPreference()); // vii. String connectionPreference
        profileOutputDTO.setProfilePic(profile.get().getProfilePic());    // viii. String profilePic
        profileOutputDTO.setHealforceName(profile.get().getHealforceName());

        return profileOutputDTO;
    }

    //Haal een MatchingProfile op via profileID
    @Transactional(readOnly = true)
    public ProfileMatchingOutputDTO getMatchingProfile(Long profileID) {
        //Gebruik van de @Query in de ProfileRepository om gegevens op te halen
        return profileRepository.findMatchingProfileById(profileID);
    }


    //Werk een profiel bij
    @Transactional
    public ProfileOutputDTO updateUserProfile(Long profileID, ProfileInputDTO profileInputDTO) {
        Profile profile = profileRepository.findById(profileID)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        profile.setDateOfBirth(profileInputDTO.getDateOfBirth());  // i. LocalDate dateOfBirth
        profile.setLocation(profileInputDTO.getLocation());        // ii. String location
        profile.setGender(profileInputDTO.getGender());            // iii. String gender
        profile.setHealthChallenge(profileInputDTO.getHealthChallenge()); // iv. String healthChallenge
        profile.setDiagnosisDate(profileInputDTO.getDiagnosisDate());     // v. YearMonth diagnosisDate
        profile.setHealingChoice(profileInputDTO.getHealingChoice()); // vi. String healingPreference
        profile.setConnectionPreference(profileInputDTO.getConnectionPreference()); // vii. String connectionPreference
        profile.setProfilePic(profileInputDTO.getProfilePic());    // viii. String profilePic
        profile.setHealforceName(profileInputDTO.getHealforceName());     // ix. String healforceName

        profileRepository.save(profile);

        ProfileOutputDTO updatedProfile = new ProfileOutputDTO();
        updatedProfile.setDateOfBirth(profile.getDateOfBirth());
        updatedProfile.setLocation(profile.getLocation());
        updatedProfile.setGender(profile.getGender());
        updatedProfile.setHealthChallenge(profile.getHealthChallenge());
        updatedProfile.setDiagnosisDate(profile.getDiagnosisDate());
        updatedProfile.setHealingChoice(profile.getHealingChoice());
        updatedProfile.setConnectionPreference(profile.getConnectionPreference());
        updatedProfile.setProfilePic(profile.getProfilePic());
        updatedProfile.setHealforceName(profile.getHealforceName());

        return updatedProfile;
    }

    //Verwijder een profiel
    public void deleteProfile(Long profileID) {
        Profile profile = profileRepository.findById(profileID)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        profileRepository.delete(profile);
    }
}