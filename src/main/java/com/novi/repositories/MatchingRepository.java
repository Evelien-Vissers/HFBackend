package com.novi.repositories;
import com.novi.entities.Matching;
import com.novi.entities.PotentialMatches;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchingRepository extends JpaRepository<Matching, Long> {

    //Zoek lijst met potentiele matches
    @Query("SELECT new com.novi.entities.PotentialMatches(p.healforceName, p.healthChallenge, p.profilePic, p.location) " +
            "FROM Profile p " +
            "WHERE (:connectionPreference = 'AllTypes' OR p.healingChoice = :connectionPreference)" +
            "AND p.id != :currentProfile")
    List<PotentialMatches> findPotentialMatches(@Param("connectionPreference") String connectionPreference,
                                                @Param("currentProfile") Long currentProfileId);

    //Check of er al een match bestaat tussen 2 profielen (voordat status 'Yes' of 'Next' wordt bijgewerkt
    @Query("Select m FROM Matching m " +
    "WHERE (m.currentProfile.id = :currentProfileId AND m.otherProfile.id = :otherProfileId) " +
    "OR (m.currentProfile.id = :otherProfileId AND m.otherProfile.id = :currentProfileId)")
    Optional<Matching> findMatchBetweenProfiles(@Param("currentProfileId") Long currentProfileId,
                                                @Param("otherProfileId") Long otherProfileId);
}