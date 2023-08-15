package ru.venzhel.fantazy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.venzhel.fantazy.model.Race;
import ru.venzhel.fantazy.model.Result;

import java.util.List;
import java.util.Optional;

public interface ResultRepository extends JpaRepository<Result, Long> {
    List<Result> findByRaceId(long RaceId);

    List<Result> findResultsByRace(Race race);

    @Modifying
    @Query("DELETE FROM Result r WHERE r.race = :race")
    void deleteAllByRace(@Param("race") Race race);

    @Query("SELECT SUM(r.points) FROM Result r WHERE r.athlete.id = :athleteId AND r.race.event.id = :eventId")
    Optional<Integer> findPointsByAthleteIdAndEventId(@Param("athleteId") Long athleteId, @Param("eventId") Long eventId);
}
