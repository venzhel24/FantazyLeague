package ru.venzhel.fantazy.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.venzhel.fantazy.model.Athlete;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
@ActiveProfiles("test")
class AthleteRepositoryTest {

    @Autowired
    AthleteRepository athleteRepository;

    @Test
    public void testFindAthleteByName_AthleteFound() {
        String name = "Athlete1";
        Optional<Athlete> result = athleteRepository.findAthleteByName(name);

        // Verify the result
        assertTrue(result.isPresent());
    }

    @Test
    public void testFindAthleteByName_AthleteNotFound() {
        String name = "John Doe";
        Optional<Athlete> result = athleteRepository.findAthleteByName(name);

        assertFalse(result.isPresent());
    }

}