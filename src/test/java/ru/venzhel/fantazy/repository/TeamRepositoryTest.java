package ru.venzhel.fantazy.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.venzhel.fantazy.model.Team;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class TeamRepositoryTest {

    @Autowired
    TeamRepository teamRepository;

    @Test
    void findByEventIdAndUserId_TeamFound() {
        long eventId = 1L;
        long userId = 1L;

        Optional<Team> team = teamRepository.findByEventIdAndUserId(eventId, userId);

        assertTrue(team.isPresent());
        assertEquals(eventId, team.get().getEvent().getId());
        assertEquals(userId, team.get().getUser().getId());
    }

    @Test
    void findByEventIdAndUserId_TeamNotFound() {
        long eventId = 5L;
        long userId = 5L;

        Optional<Team> team = teamRepository.findByEventIdAndUserId(eventId, userId);

        assertFalse(team.isPresent());
    }

    @Test
    void findByEventId_TeamFound() {
        long eventId = 1L;

        List<Team> teamList = teamRepository.findByEventId(eventId);

        assertFalse(teamList.isEmpty());
        assertEquals(eventId, teamList.get(0).getEvent().getId());
    }

    @Test
    void findByEventId_TeamNotFound() {
        long eventId = 5L;

        List<Team> teamList = teamRepository.findByEventId(eventId);

        assertTrue(teamList.isEmpty());
    }

    @Test
    void existsTeamByEventIdAndUserId_Exists() {
        long eventId = 1L;
        long userId = 1L;

        boolean exists = teamRepository.existsTeamByEventIdAndUserId(eventId, userId);

        assertTrue(exists);
    }

    @Test
    void existsTeamByEventIdAndUserId_NotExists() {
        long eventId = 5L;
        long userId = 5L;

        boolean exists = teamRepository.existsTeamByEventIdAndUserId(eventId, userId);

        assertFalse(exists);
    }
}