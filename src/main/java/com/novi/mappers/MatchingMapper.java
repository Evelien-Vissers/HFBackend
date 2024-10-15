package com.novi.mappers;

import com.novi.dtos.PotentialMatchesOutputDTO;
import com.novi.entities.PotentialMatches;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MatchingMapper {

    // Map een PotentialMatchList naar PotentialMatchOutputDTO
    public static PotentialMatchesOutputDTO toDTO(PotentialMatches match) {
        return new PotentialMatchesOutputDTO(match.getHealforceName(), match.getHealthChallenge(), match.getProfilePic(), match.getLocation());
    }

    // Map een lijst van PotentialMatchList naar een lijst van PotentialMatchOutputDTO's
    public static List<PotentialMatchesOutputDTO> toDTOList(List<PotentialMatches> matches) {
        return matches.stream()
                .map(MatchingMapper::toDTO)  // Voor elk item in de lijst, gebruik de toDTO-methode
                .collect(Collectors.toList());
    }
}
