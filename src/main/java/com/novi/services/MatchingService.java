package com.novi.services;

import com.novi.entities.Matching;
import com.novi.entities.Profile;
import com.novi.exceptions.ResourceNotFoundException;
import com.novi.repositories.MatchingRepository;
import com.novi.repositories.ProfileRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MatchingService {

    private final MatchingRepository matchingRepository;
    private final ProfileRepository profileRepository;

    public MatchingService(MatchingRepository matchingRepository, ProfileRepository profileRepository) {
        this.matchingRepository = matchingRepository;
        this.profileRepository = profileRepository;
    }

    // 1. Methode om het huidige profiel (Profile1) van de ingelogde gebruiker op te halen
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

    // 2. Methode om de "Yes" van een gebruiker op te slaan
    @Transactional
    public boolean handleYesPress(UUID profile2Id) {
        // Haal profiel van de ingelogde gebruiker op
        Profile profile1 = getCurrentProfile(); //Ingelogde gebruiker is profile1
        Profile profile2 = getProfileById(profile2Id); // Haal profiel andere gebruiker op

        //Controleer of een match is tussen profile1 en profile2
        Matching match = findOrCreateMatching(profile1, profile2);

        // Controleer of beide profielen nu "Yes" hebben gedrukt
        if (match.getStatusProfile1()) {
            match.setStatusProfile1(true); // Profile1 drukt "Yes"
            matchingRepository.save(match);
        }

        //Controleer of beide profielen nu "Yes" hebben gedrukt
        if (match.getStatusProfile1() && match.getStatusProfile2()) {
            match.setMatchStatus(true); //Volledige match
            match.setMatchDate(LocalDateTime.now());
            matchingRepository.save(match);
            return true; // Een volledige match is gemaakt

        }
        return false; //Wachten op andere gebruiker
    }

    //3. Verwerk "Next" actie voor een match
    public void handleNoPress(UUID profile2Id) {
        Profile profile1 = getCurrentProfile();
        Profile profile2 = getProfileById(profile2Id);

        Matching match = findOrCreateMatching(profile1, profile2);
        match.setStatusProfile1(false); //Profile 1 heeft "Next" gedrukt
        match.setMatchStatus(false); // Geen Match
        matchingRepository.save(match);
    }

    // 4. Helper-methode om ene profiel op te halen via ID
    private Profile getProfileById(UUID profileId) {
        return profileRepository.findById(profileId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found for ID: " + profileId));
    }

    //5. Methode om te zoeken naar een bestaande match of een nieuwe aan te maken als deze niet bestaat
    private Matching findOrCreateMatching(Profile profile1, Profile profile2) {
        return matchingRepository.findMatchBetweenProfiles(profile1.getProfileID(), profile2.getProfileID())
                .orElseGet(() -> createNewMatching(profile1, profile2));
    }

    //6. Methode om een nieuwe match aan te maken
    private Matching createNewMatching(Profile profile1, Profile profile2) {
        Matching newMatch = new Matching();
        newMatch.setProfile1(profile1);
        newMatch.setProfile2(profile2);
        newMatch.setStatusProfile1(false); // Standaard false, totdat profile1 kiest
        newMatch.setStatusProfile2(false); //Standaard false, todat Profile 2 kiest
        return newMatch;
    }
}







