package com.novi.repositories;
import com.novi.entities.Matching;
import com.novi.entities.PotentialMatchList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchingRepository extends JpaRepository<Matching, Long> {

    @Query("SELECT new com.novi.entities.PotentialMatchList(p.healforceName, p.healthChallenge, p.profilePic, p.location) " +
            "FROM Profile p " +
            "WHERE (:connectionPreference = 'AllTypes' OR p.healingChoice = :connectionPreference)")
    List<PotentialMatchList> findMatchingProfiles(@Param("connectionPreference") String connectionPreference);
}