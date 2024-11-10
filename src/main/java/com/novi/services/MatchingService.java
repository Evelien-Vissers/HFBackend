package com.novi.services;

import com.novi.dtos.CurrentMatchesOutputDTO;
import com.novi.entities.CurrentMatches;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class MatchingService {

    private final MatchingRepository matchingRepository;
    private final ProfileRepository profileRepository;

    public MatchingService(MatchingRepository matchingRepository, ProfileRepository profileRepository) {
        this.matchingRepository = matchingRepository;
        this.profileRepository = profileRepository;
    }

    public Profile getCurrentProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            return profileRepository.findByEmail(username)
                    .orElseThrow(() -> new ResourceNotFoundException("Current profile not found for email: " + username));
        } else {
            throw new IllegalArgumentException("You are not logged in");
        }
    }

    @Transactional
    public boolean handleYesPress(Long profile2Id) {
        Profile profile1 = getCurrentProfile();
        Profile profile2 = getProfileById(profile2Id);

        Optional<Matching> existingMatch = matchingRepository.findMatchBetweenProfiles(profile1.getId(), profile2.getId());

        if (existingMatch.isPresent()) {
            Matching match = existingMatch.get();
                match.setStatusProfile2(true);

        if (Boolean.TRUE.equals(match.getStatusProfile1()) && Boolean.TRUE.equals(match.getStatusProfile2())) {
            match.setMatchStatus(true);
            match.setMatchDate(LocalDateTime.now());
        } else {
            match.setMatchStatus(false);
        }
            matchingRepository.save(match);
            return match.getMatchStatus() != null && match.getMatchStatus();
        } else {
            Matching newMatch = new Matching();
            newMatch.setProfile1(profile1);
            newMatch.setProfile2(profile2);
            newMatch.setStatusProfile1(true);
            newMatch.setStatusProfile2(null);
            newMatch.setMatchStatus(false);
            matchingRepository.save(newMatch);
            return false;
        }
    }

    @Transactional
    public void handleNextPress(Long profile2Id) {
        Profile profile1 = getCurrentProfile();
        Profile profile2 = getProfileById(profile2Id);

        Optional<Matching> existingMatch = matchingRepository.findMatchBetweenProfiles(profile1.getId(), profile2.getId());

        if (existingMatch.isPresent()) {
            Matching match = existingMatch.get();

                match.setStatusProfile2(false);
                match.setMatchStatus(false);
                matchingRepository.save(match);
            } else {
                Matching newMatch = new Matching();
                newMatch.setProfile1(profile1);
                newMatch.setProfile2(profile2);
                newMatch.setStatusProfile1(false);
                newMatch.setStatusProfile2(null);
                newMatch.setMatchStatus(false);
                matchingRepository.save(newMatch);
            }
        }

        public Profile getProfileById(Long id) {
            return profileRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Profile not found for ID: " + id));
        }


    public List<CurrentMatchesOutputDTO> getMyMatches() {
        Profile currentProfile = getCurrentProfile();
        List<CurrentMatches> currentMatches = matchingRepository.findMatchesByCurrentProfile(currentProfile.getId());

        return currentMatches.stream()
                .map(match -> new CurrentMatchesOutputDTO(match.getHealforceName(), match.getProfileId(), match.getEmail()))
                .collect(Collectors.toList());
    }
}