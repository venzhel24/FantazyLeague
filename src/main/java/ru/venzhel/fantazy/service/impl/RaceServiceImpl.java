package ru.venzhel.fantazy.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import ru.venzhel.fantazy.model.Event;
import ru.venzhel.fantazy.model.Race;
import ru.venzhel.fantazy.model.RaceType;
import ru.venzhel.fantazy.repository.RaceRepository;
import ru.venzhel.fantazy.service.RaceService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j
public class RaceServiceImpl implements RaceService {
    private final RaceRepository raceRepository;
    @Override
    public Race getById(long id) {
        return raceRepository.findById(id).orElse(null);
    }

    @Override
    public List<Race> getAll() {
        List<Race> races = raceRepository.findAll();
        if (races.isEmpty()) {
            return null;
        }
        return races;
    }

    @Override
    public List<Race> getByEventId(long eventId) {
        List<Race> races = raceRepository.findByEventId(eventId);
        if (races.isEmpty()) {
            return null;
        }
        return races;
    }

    @Override
    public Race saveRace(Race race) {
        return raceRepository.save(race);
    }

    @Override
    public void deleteById(long id) {
        raceRepository.deleteById(id);
    }

    @Override
    public boolean isRaceExistsByEventAndRaceType(Event event, RaceType raceType) {
        return raceRepository.existsByEventAndRaceType(event, raceType);
    }
}
