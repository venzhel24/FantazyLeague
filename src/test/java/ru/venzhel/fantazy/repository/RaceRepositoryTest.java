package ru.venzhel.fantazy.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.venzhel.fantazy.model.Event;
import ru.venzhel.fantazy.model.Race;
import ru.venzhel.fantazy.model.RaceType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class RaceRepositoryTest {
    @Autowired
    RaceRepository raceRepository;

    @Autowired
    EventRepository eventRepository;


    @Test
    void findByEventId_RaceFound() {
        long eventId = 1L;
        List<Race> raceList = raceRepository.findByEventId(eventId);

        assertFalse(raceList.isEmpty());
        assertEquals(eventId, raceList.get(0).getEvent().getId());
    }

    @Test
    void findByEventId_RaceNotFound() {
        long eventId = 500L;
        List<Race> raceList = raceRepository.findByEventId(eventId);

        assertTrue(raceList.isEmpty());
    }

    @Test
    void existsByEventAndRaceType_RaceExists() {
        Event event = eventRepository.findById(1L).get();
        RaceType raceType = RaceType.SPRINT;

        boolean exists = raceRepository.existsByEventAndRaceType(event, raceType);

        assertTrue(exists);
    }

    @Test
    void existsByEventAndRaceType_RaceNotExists() {
        Event event = eventRepository.findById(3L).get();
        RaceType raceType = RaceType.SPRINT;

        boolean exists = raceRepository.existsByEventAndRaceType(event, raceType);

        assertFalse(exists);
    }
}