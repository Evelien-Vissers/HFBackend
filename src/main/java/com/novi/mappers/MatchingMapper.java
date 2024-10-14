package com.novi.mappers;

import com.novi.entities.PotentialMatches;

public class MatchingMapper {

    // Map een PotentialMatchList naar PotentialMatchOutputDTO
    public static PotentialMatchesOutputDTO toDTO(PotentialMatches match) {
        return new PotentialMatchOutputDTO(match.getHealforceName(), match.getHealthChallenge(), match.getProfilePic(), match.getLocation());
    }

    // Map een lijst van PotentialMatchList naar een lijst van PotentialMatchOutputDTO's
    public static List<PotentialMatchesOutputDTO> toDTOList(List<PotentialMatchesList> matches) {
        return matches.stream()
                .map(MatchingMapper::toDTO)  // Voor elk item in de lijst, gebruik de toDTO-methode
                .collect(Collectors.toList());
    }
}
