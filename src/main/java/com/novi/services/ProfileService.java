package main.java.com.novi.services;
//Dit is de service die de businesslogica afhandelt voor het ophalen en bijwerken van profielinformatie

import main.java.com.novi.repositories.ProfileRepository;
import main.java.com.novi.entities.Profile;
import java.util.List;
import java.util.Optional;

public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
}

// Profiel opslaan
public void saveProfile (Profile profile) {
    profileRepository.save(profile);
}

//Haal alle profielen op
public List<Profile> getAllProfiles() {
    return profileRepository.findAll();
}

//Haal een specifiek profiel op via ID
public Profile getProfileById(Long id) {
    return profileRepository.findById(id).orElse(null);
}

//Update een bestaand profiel
public Profile updateProfile(Long id, Profile updatedProfile) {
    Optional<Profile> existingProfile = profileRepository.findById(id);
    if (existingProfile.isPresent()) {
        Profile profile = existingProfile.get();
        profile.setDateOfBirth(updatedProfile.getDateOfBirth());
        profile.setLocation(updatedProfile.getLocation());
        profile.setGender(updatedProfile.getGender());
        profile.setHealthChallenge(updatedProfile.getHealthChallenge());
        profile.setDiagnosisDate(updatedProfile.getDiagnosisDate());
        profile.setHealingChoice(updatedProfile.getHealingChoice());
        profile.setConnectionPreference(updatedProfile.getConnectionPreference());
        profile.setProfilePic(updatedProfile.getProfilePic());
        return profileRepository.save(profile);
    }
    return null;
}

    public void deleteProfile (Long id){
        if (profileRepository.existsById(id)) {
            profileRepository.deleteById(id);
        }
    }
}
