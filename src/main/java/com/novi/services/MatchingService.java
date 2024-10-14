package com.novi.services;

import com.novi.entities.PotentialMatches;
import com.novi.repositories.MatchingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchingService {

    private final MatchingRepository matchingRepository;
    private final ProfileService profileService;  // Eventueel nodig als extra profielinformatie moet worden opgehaald

    public MatchingService(MatchingRepository matchingRepository, ProfileService profileService) {
        this.matchingRepository = matchingRepository;
        this.profileService = profileService;
    }

    // Methode die de lijst met potentiële matches ophaalt voor profileID1
    public List<PotentialMatches> getPotentialMatches(Long profileID1) {
        // Haal de connectionPreference van profileID1 op (dit kan uit een profiel of andere bron komen)
        String connectionPreference = getConnectionPreferenceForUser(profileID1);

        // Haal de potentiële matches op uit de repository
        List<PotentialMatches> potentialMatches = matchingRepository.potentialMatchList(connectionPreference, profileID1);

        // Converteer de lijst van PotentialMatchList naar een lijst van PotentialMatcOutputhDTO's
        return MatchingMapper.toDTOList(potentialMatches);
    }

    // Hulpmethode om de connectionPreference voor de gebruiker (profileID1) op te halen
    private String getConnectionPreferenceForUser(Long profileID1) {
        // Haal het profiel van de gebruiker op via ProfileService of ProfileRepository
        Profile profile = profileService.getProfileById(profileID1);

        // Return de connectionPreference van het profiel
        return profile.getConnectionPreference();
    }
}

