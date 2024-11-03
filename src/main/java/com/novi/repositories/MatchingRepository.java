package com.novi.repositories;
import com.novi.entities.CurrentMatches;
import com.novi.entities.Matching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchingRepository extends JpaRepository<Matching, Long> {

    //Check of er al een match bestaat tussen 2 profielen
    @Query("SELECT m FROM Matching m " +
            "WHERE (m.profile1.id = :profile1Id AND m. profile2.id = :profile2Id) " +
            "OR (m.profile1.id = :profile2Id AND m.profile2.id = :profile1Id)")

    Optional<Matching> findMatchBetweenProfiles(@Param("profile1Id") Long profile1Id,
                                                @Param("profile2Id") Long profile2Id);

    //Haal alle voltooide matches voor de huidige gebruiker op
    @Query("SELECT new com.novi.entities.CurrentMatches(p2.healforceName, p2.id, p2.user.email) " +
           "FROM Matching m " +
            "JOIN m.profile1 p1 " +
            "JOIN m.profile2 p2 " +
            "WHERE p1.id = :currentProfileId AND m.matchStatus = true")
    List<CurrentMatches> findMatchesByCurrentProfile(@Param("currentProfileId") Long currentProfileId);
}