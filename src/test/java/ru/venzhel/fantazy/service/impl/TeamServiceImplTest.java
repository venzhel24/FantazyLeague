package ru.venzhel.fantazy.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.venzhel.fantazy.dto.TeamAndPointsDto;
import ru.venzhel.fantazy.model.Athlete;
import ru.venzhel.fantazy.model.Event;
import ru.venzhel.fantazy.model.Team;
import ru.venzhel.fantazy.model.User;
import ru.venzhel.fantazy.repository.ResultRepository;
import ru.venzhel.fantazy.repository.TeamRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TeamServiceImplTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private ResultRepository resultRepository;

    @InjectMocks
    private TeamServiceImpl teamService;

    @Test
    void getTeamByEventAndUserTestPositive() {
        // given
        long eventId = 1L;

        User currentUser = new User();
        currentUser.setId(1L);
        currentUser.setUsername("user1");
        currentUser.setUsername("password");

        Event event = new Event();
        event.setId(eventId);
        event.setCity("city1");

        Team expectedTeam = new Team();
        expectedTeam.setId(1L);
        expectedTeam.setUser(currentUser);
        expectedTeam.setEvent(event);

        when(userService.getCurrentUser()).thenReturn(currentUser);
        when(teamRepository.findByEventIdAndUserId(eventId, currentUser.getId())).thenReturn(Optional.of(expectedTeam));

        // when
        Team team = teamService.getTeamByEventAndUser(event);

        // then
        assertNotNull(team);
        assertEquals(expectedTeam, team);
    }

    @Test
    void getTeamByEventAndUserTestNegative() {
        // given
        long eventId = 1L;
        Event event = new Event();
        event.setId(eventId);
        event.setCity("city1");

        User currentUser = new User();
        currentUser.setId(1L);
        currentUser.setUsername("user1");
        currentUser.setUsername("password");

        when(userService.getCurrentUser()).thenReturn(currentUser);
        when(teamRepository.findByEventIdAndUserId(eventId, currentUser.getId())).thenThrow(new RuntimeException());

        // when
        Team team = teamService.getTeamByEventAndUser(event);

        // then
        assertNull(team);
    }

    @Test
    public void testGetTeamAndPointsListByEventId_WithTeams_ReturnsSortedDtoList() {
        // Arrange
        long eventId = 1L;

        Event event = new Event();
        event.setId(eventId);

        Team team1 = new Team();
        team1.setAthlete1(new Athlete());
        team1.setAthlete2(new Athlete());
        team1.setAthlete3(new Athlete());
        team1.setEvent(event); // Set the event for team1

        Team team2 = new Team();
        team2.setAthlete1(new Athlete());
        team2.setAthlete2(new Athlete());
        team2.setAthlete3(new Athlete());
        team2.setEvent(event); // Set the event for team2

        List<Team> teams = List.of(team1, team2);
        when(teamRepository.findByEventId(eventId)).thenReturn(teams);

        // Mock the resultRepository.findPointsByAthleteIdAndEventId() method
        when(resultRepository.findPointsByAthleteIdAndEventId(anyLong(), eq(eventId))).thenReturn(Optional.of(10));

        // Act
        List<TeamAndPointsDto> result = teamService.getTeamsAndPointsListByEventId(eventId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(team2, result.get(0).getTeam());
        assertEquals(30, result.get(0).getTotalPoints());
        assertEquals(team1, result.get(1).getTeam());
        assertEquals(30, result.get(1).getTotalPoints());
    }


    @Test
    public void testGetTeamAndPointsListByEventId_WithNoTeams_ReturnsNull() {
        // Arrange
        long eventId = 2L;
        List<Team> emptyList = Collections.emptyList();
        when(teamRepository.findByEventId(eventId)).thenReturn(emptyList);

        // Act
        List<TeamAndPointsDto> result = teamService.getTeamsAndPointsListByEventId(eventId);

        // Assert
        assertNull(result);
    }
}