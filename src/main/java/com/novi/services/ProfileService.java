package com.novi.services;

import com.novi.dtos.ProfileInputDTO;
import com.novi.dtos.ProfileOutputDTO;
import com.novi.entities.MiniProfile;
import com.novi.entities.Profile;
import com.novi.entities.User;
import com.novi.exceptions.ResourceNotFoundException;
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

    // 1a. Sla een nieuw profiel op met de bijbehorende ID
    @Transactional
    public void saveProfile(Long ID, ProfileInputDTO profileInputDTO) {
        //Zoek de gebruiker obv ID
        User user = userRepository.findById(profileInputDTO.getID())
                .orElseThrow(() -> new RuntimeException("User not found"));

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

    //1b. Genereer een profileID en sla het op
    public Long generateProfileID(ProfileInputDTO profileInputDTO) {
        User user = userRepository.findByEmail(profileInputDTO.findByEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        //Zoek het profiel dat aan de gebruiker is gekoppeld
        Profile profile = profileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Profile not found for user"));

        return profile.getId(); //De profileID is het ID van het profiel dat aan de gebruiker (ID) is gekoppeld.
    }

    //2. Haal een profiel op van een specifieke gebruiker adhv profileID
    public ProfileOutputDTO getProfileByProfileID(Long profileID) {
        Profile profile = profileRepository.findById(profileID)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found"));

        ProfileOutputDTO profileOutputDTO = new ProfileOutputDTO();
        profileOutputDTO.setDateOfBirth(profile.getDateOfBirth());  // i. LocalDate dateOfBirth
        profileOutputDTO.setLocation(profile.getLocation());        // ii. String location
        profileOutputDTO.setGender(profile.getGender());            // iii. String gender
        profileOutputDTO.setHealthChallenge(profile.getHealthChallenge()); // iv. String healthChallenge
        profileOutputDTO.setDiagnosisDate(profile.getDiagnosisDate());     // v. YearMonth diagnosisDate
        profileOutputDTO.setHealingChoice(profile.getHealingChoice()); // vi. String healingPreference
        profileOutputDTO.setConnectionPreference(profile.getConnectionPreference()); // vii. String connectionPreference
        profileOutputDTO.setProfilePic(profile.getProfilePic());    // viii. String profilePic
        profileOutputDTO.setHealforceName(profile.getHealforceName());

        return profileOutputDTO;
    }

    //4. Haal een Mini Profile op via profileID (van gebruiker zelf)
    @Transactional(readOnly = true)
    public MiniProfile getMiniProfile(Long profileID) {
        //Gebruik van de @Query in de ProfileRepository om gegevens op te halen
        return profileRepository.findMiniProfileById(profileID);
    }


    //5. Werk een profiel bij
    @Transactional
    public ProfileOutputDTO updateProfile(Long profileID, ProfileInputDTO profileInputDTO) {
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

    //6. Verwijder een profiel
    public void deleteProfile(Long profileID) {
        Profile profile = profileRepository.findById(profileID)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        profileRepository.delete(profile);
    }

    //7. Sla de gegenereerde profileID op (placeholder functie)
    public void saveProfileID(Long profileID) {
    }

    //8. Methode om het profiel-ID van de gebruiker op te halen via e-mail
    public Long getProfileIdByEmail(String email) {
        //Zoek het profiel obv het emailadres van de gebruiker
        Profile profile = profileRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found"));

        //Retourneer het profiel-ID
        return profile.getId();
    }

    //9. Methode om het profiel van de huidige ingelogde gebruiker op te halen
    public Long getCurrentUserProfileId() {
        //Haal gebruikersnaam of email op van de ingelogde gebruiker via Spring Security
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email;

        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUserName();
        } else {
            email = principal.toString();
        }
        //Haal de User entity op uit de database obv de email
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Haal het profiel-ID van de ingelogde gebruiker op
        Profile profile = profileRepository.findByUser(user).orElseThrow(() -> new IllegalArgumentException("Profile not found"));
        return profile.getId(); // Retourneer het profiel-ID van de huidige ingelogde gebruiker
    }

}