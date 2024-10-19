package com.novi.services;

import com.novi.dtos.PotentialMatchesOutputDTO;
import com.novi.entities.Matching;
import com.novi.entities.PotentialMatches;
import com.novi.entities.Profile;
import com.novi.exceptions.ResourceNotFoundException;
import com.novi.mappers.MatchingMapper;
import com.novi.repositories.MatchingRepository;
import com.novi.repositories.ProfileRepository;
import com.novi.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MatchingService {

    private final MatchingRepository matchingRepository;
    private final ProfileService profileService;
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    public MatchingService(MatchingRepository matchingRepository, ProfileService profileService, ProfileRepository profileRepository, UserRepository userRepository) {
        this.matchingRepository = matchingRepository;
        this.profileService = profileService;
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    // 1. Methode om het huidige profiel (currentProfile) van de ingelogde gebruiker op te halen
    public Profile getCurrentProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            //Gebruik emailadres om het profiel op te halen
            return profileRepository.findByEmail(username)
                    .orElseThrow(() -> new ResourceNotFoundException("Current profile not found for email: " + username));
        } else {
            throw new IllegalArgumentException("You are not logged in");
        }
    }

    // 2. Methode die de lijst met potentiële matches ophaalt voor currentProfile
    public List<PotentialMatchesOutputDTO> findPotentialMatches() {
        //Haal het profiel van de ingelogde gebruiker op
        Profile currentProfile = getCurrentProfile();

        // Haal de connectionPreference van het profiel op
        String connectionPreference = currentProfile.getConnectionPreference();

        // Haal de potentiële matches op uit de repository
        List<PotentialMatches> potentialMatches = matchingRepository.findPotentialMatches(connectionPreference, currentProfile.getId());

        // Converteer de lijst van PotentialMatchList naar een lijst van PotentialMatchOutputDTO's
        return MatchingMapper.toDTOList(potentialMatches);
    }


    // 3. Methode om de "Yes" of "No" van een gebruiker op te slaan
    // 3A. Verwerk "Yes" actie voor een match
    public boolean handleYesPress(Long otherProfileID) {
    // Haal profiel van de ingelogde gebruiker op
    Profile currentProfile = getCurrentProfile();

    //Controleer of er een match is tussen currentProfile en otherProfile
    Optional<Matching> existingMatch = matchingRepository.findMatchBetweenProfiles(currentProfile.getId(), otherProfileId);

    if (existingMatch.isPresent()) {
        Matching match = existingMatch.get();

        // Controleer of profileID1 al "Yes" heeft gedrukt, zo niet, stel in
        if (!match.getStatusProfile1()) {
            match.setStatusProfile1(true);
            matchingRepository.save(match);
        }

        // Controleer of beide profielen nu "Yes" hebben gedrukt
        if (match.getStatusProfile1() && match.getStatusOtherProfile()) {
            match.setMatchStatus(true); // Volledige match
            match.setMatchDate(LocalDateTime.now());
            matchingRepository.save(match);
            return true; // Een volledige match is gemaakt
        } else {
            // Alleen status van Profile1 bijgewerkt, wacht op Profile2
            return false;
        }

    } else {
        // Geen bestaande match, maak een nieuwe
        Matching newMatch = new Matching();
        //newMatch.setProfile1(profileID1);
        //newMatch.setProfile2(profileID2);
        newMatch.setStatusProfile1(true); // Profile1 drukt "Yes"
        newMatch.setStatusProfile2(false); // Wacht op Profile2
        matchingRepository.save(newMatch);
        return false;
    }
}

// 3B. Verwerk "No" actie voor een match
public void handleNoPress(Long profileID1, Long profileID2) {
    // Controleer of er een match bestaat
    Optional<Matching> existingMatch = matchingRepository.findMatchBetweenProfiles(profileID1, profileID2);

    if (existingMatch.isPresent()) {
        Matching match = existingMatch.get();
        match.setStatusProfile1(false); // Profile1 heeft "No" gedrukt
        match.setMatchStatus(false); // Geen match
        matchingRepository.save(match);
    } else {
        // Als er nog geen match is, maak een nieuwe met "No"
        Matching newMatch = new Matching();
       // newMatch.setProfile1(profileID1);
       // newMatch.setProfile2(profileID2);
        newMatch.setStatusProfile1(false); // Profile1 drukt "No"
        newMatch.setStatusProfile2(false); // Wacht niet op Profile2
        newMatch.setMatchStatus(false); // Geen match mogelijk
        matchingRepository.save(newMatch);
    }
}}






