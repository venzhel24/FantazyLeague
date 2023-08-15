package ru.venzhel.fantazy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.venzhel.fantazy.model.Event;
import ru.venzhel.fantazy.model.Race;
import ru.venzhel.fantazy.model.RaceType;

import java.util.List;

public interface RaceRepository extends JpaRepository<Race, Long> {
    List<Race> findByEventId(long EventId);

    boolean existsByEventAndRaceType(Event event, RaceType raceType);
}
