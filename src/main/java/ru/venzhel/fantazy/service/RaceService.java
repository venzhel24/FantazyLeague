package ru.venzhel.fantazy.service;

import ru.venzhel.fantazy.model.Event;
import ru.venzhel.fantazy.model.Race;
import ru.venzhel.fantazy.model.RaceType;

import java.util.List;

public interface RaceService {
    Race getById(long id);

    List<Race> getAll();

    List<Race> getByEventId(long id);

    Race saveRace(Race race);

    void deleteById(long id);

    boolean isRaceExistsByEventAndRaceType(Event event, RaceType raceType);
}
