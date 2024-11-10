package com.novi.mappers;

import com.novi.dtos.PotentialMatchesOutputDTO;
import com.novi.entities.PotentialMatches;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MatchingMapper {

    public static PotentialMatchesOutputDTO toDTO(PotentialMatches potentialMatch) {
        return new PotentialMatchesOutputDTO(
                potentialMatch.getId(),
                potentialMatch.getHealforceName(),
                potentialMatch.getHealthChallenge(),
                potentialMatch.getHealingChoice(),
                potentialMatch.getProfilePicUrl(),
                potentialMatch.getCity(),
                potentialMatch.getCountry()
        );
    }

    public static List<PotentialMatchesOutputDTO> toDTOList(List<PotentialMatches> potentialMatchesList) {
        return potentialMatchesList.stream()
                .map(MatchingMapper::toDTO)
                .collect(Collectors.toList());
    }
}
