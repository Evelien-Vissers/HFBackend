package com.novi.repositories;

import com.novi.entities.MiniProfile;
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

    // Methode om een profiel te vinden obv de gekoppelde user
    Optional<Profile> findByUser(User user);

    //Methode om een gebruiker te vinden obv email
    @Query("SELECT p FROM Profile p WHERE p.user.email = :email")
    Optional<Profile> findByEmail(@Param("email") String email);


    // Gerichte query om alleen de benodigde velden op te halen voor een MiniProfile van gebruiker zelf
    @Query("SELECT new com.novi.entities.MiniProfile(p.healforceName, p.healthChallenge, p.profilePicUrl, p.city, p.country) " + "" +
            "FROM Profile p " +
            "WHERE p.id = :profileID")
    Optional<MiniProfile> findMiniProfileById(@Param("profileID") Long profileID);

    //Zoek lijst met potentiele matches (van andere profielen)
    @Query("SELECT new com.novi.entities.PotentialMatches(p.healforceName, p.healthChallenge, p.profilePicUrl, p.city, p.country) " +
            "FROM Profile p " +
            "WHERE (:connectionPreference = 'AllTypes' OR p.healingChoice = :connectionPreference)" +
            "AND p.id != :currentProfile")
    List<PotentialMatches> findPotentialMatches(@Param("connectionPreference") String connectionPreference,
                                                @Param("currentProfile") Long currentProfileId);
}

