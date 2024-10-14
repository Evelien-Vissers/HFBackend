package com.novi.repositories;
import com.novi.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    default List<Message> findByMatching_MatchingId(Long matchId) {
        return null;
    }
}
