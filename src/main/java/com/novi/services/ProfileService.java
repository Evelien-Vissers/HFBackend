package com.novi.services;

import com.novi.dtos.ProfileInputDTO;
import com.novi.dtos.ProfileOutputDTO;
import com.novi.dtos.UserInputDTO;
import com.novi.entities.MiniProfile;
import com.novi.entities.Profile;
import com.novi.entities.User;
import com.novi.mappers.ProfileMapper;
import com.novi.repositories.ProfileRepository;
import com.novi.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final ProfileMapper profileMapper;

    public ProfileService(ProfileRepository profileRepository, UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
        this.profileMapper = new ProfileMapper();
    }

    // 1. Sla een nieuw profiel op met de bijbehorende ID
    @Transactional
    public void saveProfile(ProfileInputDTO profileInputDTO) {
        //Zoek de gebruiker obv ID
        User user = userRepository.findById(profileInputDTO.getProfileID())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Profile profile = profileMapper.toEntity(profileInputDTO);
        profile.setUser(user);
        profileRepository.save(profile); //UUID wordt autmoatisch gegenereerd in Profile entity
    }


    // 2. Haal een profiel op van een specifieke gebruiker adhv profileID
    public ProfileOutputDTO getUserProfileByProfileID(UUID profileID) {
        return profileRepository.findById(profileID)
                .map(profileMapper::toProfileOutputDTO)
                .orElse(null);
    }

    //3. Haal een Mini Profile op via profileID (van gebruiker zelf)
    @Transactional(readOnly = true)
    public Optional<MiniProfile> getMiniProfile(UUID profileID) {
        //Gebruik van de @Query in de ProfileRepository om gegevens op te halen
        return profileRepository.findMiniProfileById(profileID);
    }

    //5. Werk een profiel bij (UUID)
    @Transactional
    public ProfileOutputDTO updateProfile(UUID profileID, ProfileInputDTO profileInputDTO) {
        Profile profile = profileRepository.findById(profileID)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        profileMapper.toEntity(profileInputDTO);
        profileRepository.save(profile);

        return profileMapper.toProfileOutputDTO(profile);
    }


    //6. Verwijder een profiel
    public void deleteProfile(UUID profileID) {
        Profile profile = profileRepository.findById(profileID)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        profileRepository.delete(profile);
    }

    //7. Methode om het profiel-ID van de gebruiker op te halen via e-mail
    public UUID getProfileIdByEmail(String email) {
        //Zoek het profiel obv het emailadres van de gebruiker
        Profile profile = profileRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found"));

        //Retourneer het profiel-ID
        return profile.getProfileID(); //UUID teruggeven.
    }

    //8. Methode om het profiel van de huidige ingelogde gebruiker op te halen
    public UUID getCurrentUserProfileId() {
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
        return profile.getProfileID(); // Retourneer het profiel-ID van de huidige ingelogde gebruiker

}}