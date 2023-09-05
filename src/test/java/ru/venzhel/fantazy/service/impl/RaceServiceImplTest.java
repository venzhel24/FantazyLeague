package ru.venzhel.fantazy.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.venzhel.fantazy.model.Event;
import ru.venzhel.fantazy.model.Race;
import ru.venzhel.fantazy.model.RaceType;
import ru.venzhel.fantazy.repository.RaceRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RaceServiceImplTest {

    @Mock
    private RaceRepository raceRepository;

    private RaceServiceImpl raceService;

    @BeforeEach
    public void setup() {
        raceService = new RaceServiceImpl(raceRepository);
    }

    @Test
    public void testGetAll_WithRaces_ReturnsList() {
        // Arrange
        List<Race> races = new ArrayList<>();
        Race race1 = new Race();
        race1.setRaceType(RaceType.INDIVIDUAL);
        races.add(race1);

        Race race2 = new Race();
        race2.setRaceType(RaceType.PURSUIT);
        races.add(race2);

        when(raceRepository.findAll()).thenReturn(races);

        // Act
        List<Race> result = raceService.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(RaceType.INDIVIDUAL, result.get(0).getRaceType());
        assertEquals(RaceType.PURSUIT, result.get(1).getRaceType());
    }

    @Test
    public void testGetAll_WithNoRaces_ReturnsNull() {
        // Arrange
        List<Race> emptyList = new ArrayList<>();
        when(raceRepository.findAll()).thenReturn(emptyList);

        // Act
        List<Race> result = raceService.getAll();

        // Assert
        assertNull(result);
    }

    @Test
    public void testGetById_WithExistingRace_ReturnsRace() {
        // Arrange
        long raceId = 1L;
        Race race = new Race();
        race.setId(raceId);
        race.setDate(LocalDate.now());

        when(raceRepository.findById(raceId)).thenReturn(Optional.of(race));

        // Act
        Race result = raceService.getById(raceId);

        // Assert
        assertNotNull(result);
        assertEquals(raceId, result.getId());
        assertEquals(LocalDate.now(), result.getDate());
    }

    @Test
    public void testGetById_WithNonExistingRace_ReturnsNull() {
        // Arrange
        long nonExistingRaceId = 2L;
        when(raceRepository.findById(nonExistingRaceId)).thenReturn(Optional.empty());

        // Act
        Race result = raceService.getById(nonExistingRaceId);

        // Assert
        assertNull(result);
    }

    @Test
    public void testGetAllByEventId_WithExistingRaces_ReturnsList() {
        // Arrange
        long eventId = 1L;
        List<Race> races = new ArrayList<>();
        Race race1 = new Race();
        race1.setId(1L);
        race1.setDate(LocalDate.now());
        races.add(race1);

        Race race2 = new Race();
        race2.setId(2L);
        race2.setDate(LocalDate.now().minusDays(1));
        races.add(race2);

        when(raceRepository.findByEventId(eventId)).thenReturn(races);

        // Act
        List<Race> result = raceService.getByEventId(eventId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(LocalDate.now(), result.get(0).getDate());
        assertEquals(2L, result.get(1).getId());
        assertEquals(LocalDate.now().minusDays(1), result.get(1).getDate());
    }

    @Test
    public void testGetAllByEventId_WithNoRaces_ReturnsNull() {
        // Arrange
        long eventId = 2L;
        List<Race> emptyList = new ArrayList<>();
        when(raceRepository.findByEventId(eventId)).thenReturn(emptyList);

        // Act
        List<Race> result = raceService.getByEventId(eventId);

        // Assert
        assertNull(result);
    }

    @Test
    public void testIsRaceExistsByEventAndRaceType_WithExistingRace_ReturnsTrue() {
        // Arrange
        Event event = new Event();
        event.setId(1L);

        RaceType raceType = RaceType.INDIVIDUAL;

        when(raceRepository.existsByEventAndRaceType(event, raceType)).thenReturn(true);

        // Act
        boolean result = raceService.isRaceExistsByEventAndRaceType(event, raceType);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testIsRaceExistsByEventAndRaceType_WithNonExistingRace_ReturnsFalse() {
        // Arrange
        Event event = new Event();
        event.setId(2L);

        RaceType raceType = RaceType.PURSUIT;

        when(raceRepository.existsByEventAndRaceType(event, raceType)).thenReturn(false);

        // Act
        boolean result = raceService.isRaceExistsByEventAndRaceType(event, raceType);

        // Assert
        assertFalse(result);
    }
}