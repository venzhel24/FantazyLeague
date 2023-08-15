package ru.venzhel.fantazy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.venzhel.fantazy.model.Team;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByEventIdAndUserId(long eventId, long UserId);
    List<Team> findByEventId(long eventId);
    boolean existsTeamByEventIdAndUserId(long eventId, long userId);
}
