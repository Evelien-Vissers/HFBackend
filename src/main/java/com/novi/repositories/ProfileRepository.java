package com.novi.repositories;
import com.novi.dtos.ProfileMatchingOutputDTO;
import com.novi.entities.Profile;
import com.novi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    // Methode om een profiel te vinden obv de gekoppelde user
    Optional<Profile> findByUser(User user);

    // Gerichte query om alleen de benodigde velden op te halen voor een matching profile
    @Query("SELECT new com.novi.dtos.ProfileMatchingOutputDTO(p.healforceName, p.healthChallenge, p.profilePic, p.location) " + "FROM Profile p WHERE p.id = :profileID")
    ProfileMatchingOutputDTO findMatchingProfileById(@Param("profileID") Long profileID);
}
