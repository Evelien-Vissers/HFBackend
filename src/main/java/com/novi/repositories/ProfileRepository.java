package com.novi.repositories;

import com.novi.entities.MiniProfile;
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

    //Methode om een gebruiker te vinden obv email
    @Query("SELECT p FROM Profile p WHERE p.user.email = :email")
    Optional<Profile> findByEmail(@Param("email") String email);

    // Gerichte query om alleen de benodigde velden op te halen voor een matching profile van gebruiker zelf
    @Query("SELECT new com.novi.entities.MiniProfile(p.healforceName, p.healthChallenge, p.profilePic, p.location) " + "" +
            "FROM Profile p " +
            "WHERE p.id = :profileID")
    MiniProfile findMatchingProfileById(@Param("profileID") Long profileID);

}

