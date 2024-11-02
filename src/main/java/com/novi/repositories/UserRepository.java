package com.novi.repositories;
import com.novi.entities.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //Zoek een gebruiker op basis van e-mailadres
    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findByEmail(String email);

}
