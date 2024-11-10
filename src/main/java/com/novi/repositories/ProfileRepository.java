package com.novi.repositories;

import com.novi.entities.PotentialMatches;
import com.novi.entities.Profile;
import com.novi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByUser(User user);

    @Query("SELECT p FROM Profile p WHERE p.user.email = :email")
    Optional<Profile> findByEmail(@Param("email") String email);

    @Query("SELECT new com.novi.entities.PotentialMatches(p. id, p.healforceName, p.healthChallenge, p.healingChoice, p.profilePicUrl, p.city, p.country) " +
            "FROM Profile p " +
            "WHERE (:connectionPreference = 'All Types' OR p.healingChoice = :connectionPreference)" +
            "AND p.id != :currentProfile")
    List<PotentialMatches> findPotentialMatches(@Param("connectionPreference") String connectionPreference,
                                                @Param("currentProfile") Long currentProfileId);
}

