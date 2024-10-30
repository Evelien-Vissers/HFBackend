package com.novi.mappers;

import com.novi.dtos.PotentialMatchesOutputDTO;
import com.novi.entities.PotentialMatches;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MatchingMapper {

    // Map een PotentialMatchList naar PotentialMatchOutputDTO
    public static PotentialMatchesOutputDTO toDTO(PotentialMatches potentialMatch) {
        return new PotentialMatchesOutputDTO(
                potentialMatch.getHealforceName(),
                potentialMatch.getHealthChallenge(),
                potentialMatch.getProfilePicUrl(),
                potentialMatch.getCity(),
                potentialMatch.getCountry()
        );
    }

    // Map een lijst van PotentialMatches naar een lijst DTO's
    public static List<PotentialMatchesOutputDTO> toDTOList(List<PotentialMatches> potentialMatchesList) {
        return potentialMatchesList.stream()
                .map(MatchingMapper::toDTO)  // Voor elk item in de lijst, gebruik de toDTO-methode
                .collect(Collectors.toList());
    }
}
