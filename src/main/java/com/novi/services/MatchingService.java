package com.novi.services;

import com.novi.dtos.PotentialMatchesOutputDTO;
import com.novi.entities.Matching;
import com.novi.entities.PotentialMatches;
import com.novi.entities.Profile;
import com.novi.exceptions.ResourceNotFoundException;
import com.novi.mappers.MatchingMapper;
import com.novi.repositories.MatchingRepository;
import com.novi.repositories.ProfileRepository;
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

    public MatchingService(MatchingRepository matchingRepository, ProfileService profileService, ProfileRepository profileRepository) {
        this.matchingRepository = matchingRepository;
        this.profileService = profileService;
        this.profileRepository = profileRepository;
    }

    // 1. Methode die de lijst met potentiële matches ophaalt voor profileID
    public List<PotentialMatchesOutputDTO> getPotentialMatches(Long profileID) {
        //Haal het profiel van de gebruiker op
        Profile profile = profileRepository.findById(profileID)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found for ID: " + profileID));

        // Haal de connectionPreference van het profiel op
        String connectionPreference = profile.getConnectionPreference();

        // Haal de potentiële matches op uit de repository
        List<PotentialMatches> potentialMatches = matchingRepository.potentialMatches(connectionPreference, profileID);

        // Converteer de lijst van PotentialMatchList naar een lijst van PotentialMatchOutputDTO's
        return MatchingMapper.toDTOList(potentialMatches);
    }

    // 2.

    // 3. Methode om de "Yes" of "No" van een gebruiker op te slaan

    // 3A. Verwerk "Yes" actie voor een match
    public boolean handleYesPress(Long profileID1, Long profileID2) {
    // Controleer of er al een match bestaat tussen de profielen
    Optional<Matching> existingMatch = matchingRepository.findMatchBetweenProfiles(profileID1, profileID2);

    if (existingMatch.isPresent()) {
        Matching match = existingMatch.get();

        // Controleer of profileID1 al "Yes" heeft gedrukt, zo niet, stel in
        if (!match.getStatusProfile1()) {
            match.setStatusProfile1(true);
            matchingRepository.save(match);
        }

        // Controleer of beide profielen nu "Yes" hebben gedrukt
        if (match.getStatusProfile1() && match.getStatusProfile2()) {
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






