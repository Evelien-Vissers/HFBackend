package main.java.com.novi.repositories;

import main.java.com.novi.entities.Matching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MatchingRepository extends JpaRepository<Matching, Long> {
    Optional<Matching> findByProfileId1AndProfileId2(Long profileId1, Long profileId2);
}
