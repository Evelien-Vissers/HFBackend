package com.novi.repositories;
import com.novi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.email = :email")
            Optional<User> findUserWithoutRolesByEmail(@Param("email") String email);
    //Zoek een gebruiker op basis van e-mailadres
    Optional<User> findByEmail(String email);

}
