package ru.venzhel.fantazy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.venzhel.fantazy.model.Sprint;

import java.util.List;

public interface SprintRepository extends JpaRepository<Sprint, Long> {
    List<Sprint> findByRaceId(long RaceId);

    @Query("SELECT SUM(s.points) FROM Sprint s WHERE s.athlete.id = :athleteId AND s.race.event.id = :eventId")
    Integer findPointsByAthleteIdAndEventId(@Param("athleteId") Long athleteId, @Param("eventId") Long eventId);
}
