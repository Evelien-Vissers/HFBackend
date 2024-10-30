package com.novi.services;

import com.novi.dtos.PotentialMatchesOutputDTO;
import com.novi.dtos.ProfileInputDTO;
import com.novi.dtos.ProfileOutputDTO;
import com.novi.dtos.ProfileQuestionnaireOutputDTO;
import com.novi.entities.MiniProfile;
import com.novi.entities.PotentialMatches;
import com.novi.entities.Profile;
import com.novi.entities.User;
import com.novi.mappers.MatchingMapper;
import com.novi.mappers.ProfileMapper;
import com.novi.repositories.ProfileRepository;
import com.novi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final ProfileMapper profileMapper;
    private final String uploadDir = "uploads/profile-pics";

    @Autowired
    public ProfileService(ProfileRepository profileRepository, UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
        this.profileMapper = new ProfileMapper();
    }

    // 0.
    public User getCurrentUser() {
        //Haal het gebruikersaccount op van de ingleogde gebruiker via Spring Security
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = ((UserDetails) authentication.getPrincipal()).getUsername();
        //Haal gebruiker op via het emailadres
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional
    public User getCurrentUserWithoutRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = ((UserDetails) authentication.getPrincipal()).getUsername();
        return userRepository.findUserWithoutRolesByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // 1. Sla een nieuw profiel op met de bijbehorende ID en afbeelding
    @Transactional
    public void saveProfile(ProfileInputDTO profileInputDTO, MultipartFile profilePic) throws IOException {
        //Gebruik de huidige ingelogde gebruiker
        User currentUser = getCurrentUserWithoutRoles();
        //Maak een nieuw profiel aan vanuit de ProfileInputDTO
        Profile profile = profileMapper.toEntity(profileInputDTO);
        //Koppel het profiel aan de huidige gebruiker
        profile.setUser(currentUser);
        //Sla het profiel eerst op in de database zodat een ID gegenereerd wordt
        profile = profileRepository.save(profile);
        //Haal het gegenereerde profileId op via het opgeslagen profiel
        Long profileID = profile.getId();
        //Sla de profielfoto op met het gegenereerde profileID
        if (profilePic != null && !profilePic.isEmpty()) {
            String profilePicUrl = saveProfilePic(profilePic, profile.getId());
            profile.setProfilePicUrl(profilePicUrl);
            profileRepository.save(profile);
        }
    }
    //Methode op profielfoto op te slaan en het pad te retourneren
    private String saveProfilePic(MultipartFile profilePic, Long profileId) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        String fileExtension = profilePic.getOriginalFilename().substring(profilePic.getOriginalFilename().lastIndexOf("."));
        String fileName = profileId + fileExtension;

        //Sla bestand op in directory
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(profilePic.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        //Retourneer het pad van de afbeelding om op te slaan in de database
        return "/uploads/profile-pics/" + fileName;
    }

    public ProfileQuestionnaireOutputDTO checkIfQuestionnaireCompleted() {
        Profile currentProfile = getCurrentProfile();
        return new ProfileQuestionnaireOutputDTO(currentProfile.getHasCompletedQuestionnaire());
    }

    // 2. Haal een profiel op van een specifieke gebruiker adhv profileID
    public ProfileOutputDTO getUserProfileByProfileID(Long id) {
        return profileRepository.findById(id)
                .map(profileMapper::toProfileOutputDTO)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
    }

    //3. Methode om het profileID van de huidige ingelogde gebruiker op te halen
    public Profile getCurrentProfileOrNull() {
        //Haal gebruikersnaam of email op van de ingelogde gebruiker via Spring Security
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = ((UserDetails) authentication.getPrincipal()).getUsername();

        // Haal het profiel van de ingelogde gebruiker op en retourneer dit
        return profileRepository.findByEmail(email).orElse(null);

    }

    // 4. Methode die de lijst met potentiële matches ophaalt voor currentProfile
    public List<PotentialMatchesOutputDTO> findPotentialMatches() {
        Optional<Profile> currentProfileOpt = Optional.ofNullable(getCurrentProfileOrNull());

        if (currentProfileOpt.isEmpty()) {
            throw new IllegalStateException("User has no profile. Please complete the questionnaire first.");
        }

        Profile currentProfile = getCurrentProfileOpt.get();
        String connectionPreference = currentProfile.getConnectionPreference();

        // Haal de potentiële matches op uit de repository
        List<PotentialMatches> potentialMatches = profileRepository.findPotentialMatches(connectionPreference, currentProfile.getId());

        //Converteer de lijst van PotentialMatches naar PotentialMatchesOutputDTO's
        return MatchingMapper.toDTOList(potentialMatches);
    }

    //5. Werk een profiel bij
    @Transactional
    public ProfileOutputDTO updateProfile(Long id, ProfileInputDTO profileInputDTO) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        profileMapper.toEntity(profileInputDTO);
        profileRepository.save(profile);

        return profileMapper.toProfileOutputDTO(profile);
    }


    //6. Verwijder een profiel
    public void deleteProfile(Long id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        profileRepository.delete(profile);
    }

    //7. Methode om het profiel-ID van de gebruiker op te halen via e-mail
    public Long getProfileIdByEmail(String email) {
        //Zoek het profiel obv het emailadres van de gebruiker
        Profile profile = profileRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found"));

        //Retourneer het profiel-ID
        return profile.getId(); //profileID teruggeven.
    }

}