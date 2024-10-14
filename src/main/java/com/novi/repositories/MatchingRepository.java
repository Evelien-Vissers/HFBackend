package com.novi.repositories;
import com.novi.entities.Matching;
import com.novi.entities.PotentialMatches;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchingRepository extends JpaRepository<Matching, Long> {

    @Query("SELECT new com.novi.entities.PotentialMatches(p.healforceName, p.healthChallenge, p.profilePic, p.location) " +
            "FROM Profile p " +
            "WHERE (:connectionPreference = 'AllTypes' OR p.healingChoice = :connectionPreference)" +
            "AND p.id != :profileID1")
    List<PotentialMatches> potentialMatchList(@Param("connectionPreference") String connectionPreference, @Param("profileID1") Long profileID1);
}