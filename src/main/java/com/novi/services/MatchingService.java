package com.novi.services;

import com.novi.dtos.PotentialMatchesOutputDTO;
import com.novi.entities.Matching;
import com.novi.entities.PotentialMatches;
import com.novi.entities.Profile;
import com.novi.mappers.MatchingMapper;
import com.novi.repositories.MatchingRepository;
import com.novi.repositories.ProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Service
public class MatchingService {

    private final MatchingRepository matchingRepository;
    private final ProfileService profileService;
    private final ProfileRepository profileRepository;

    public MatchingService(MatchingRepository matchingRepository, ProfileService profileService) {
        this.matchingRepository = matchingRepository;
        this.profileService = profileService;
        this.profileRepository = profileRepository;
    }

    // 1. Methode die de lijst met potentiële matches ophaalt voor profileID1
    public List<PotentialMatches> getPotentialMatches(Long profileID) {
        //Haal het profiel van de gebruiker op
        Profile profile = profileService.getProfileByProfileID(profileID); {

        // Haal de potentiële matches op uit de repository
        List<PotentialMatches> potentialMatches = matchingRepository.potentialMatchList(connectionPreference, profileID);

        // Converteer de lijst van PotentialMatchList naar een lijst van PotentialMatchOutputDTO's
        return MatchingMapper.toDTOList(PotentialMatchOutputDTO);
    }}

    // 2. Methode om de "Yes" van een gebruiker op te slaan
    public boolean handleYesPress (Long profileID, Long ProfileID2) {
        Optional<Matching> optionalMatch = matchingRepository.findByProfiles(profileID1, profileID2);


    }
}

