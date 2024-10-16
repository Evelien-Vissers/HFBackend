package com.novi.repositories;
import com.novi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameAndPassword(String username, String password);

    //Zoek een gebruiker op basis van e-mailadres
    Optional<User> findByEmail(String email);

    // Query om alleen de 'firstName' van de gebruiker op te halen obv ID
    @Query("SELECT u.firstName FROM User u WHERE u.id = :ID")
    Optional<String> findFirstNameById(@Param("ID") Long ID);
}
