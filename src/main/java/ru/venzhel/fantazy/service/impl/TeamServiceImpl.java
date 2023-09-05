package ru.venzhel.fantazy.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import ru.venzhel.fantazy.dto.*;
import ru.venzhel.fantazy.model.Athlete;
import ru.venzhel.fantazy.model.Event;
import ru.venzhel.fantazy.model.Team;
import ru.venzhel.fantazy.model.User;
import ru.venzhel.fantazy.repository.AthleteRepository;
import ru.venzhel.fantazy.repository.EventRepository;
import ru.venzhel.fantazy.repository.ResultRepository;
import ru.venzhel.fantazy.repository.TeamRepository;
import ru.venzhel.fantazy.service.TeamService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final AthleteRepository athleteRepository;
    private final EventRepository eventRepository;
    private final ResultRepository resultRepository;
    private final UserServiceImpl userService;
    @Override
    public Team getTeamById(long teamId) {
        return teamRepository.findById(teamId).orElse(null);
    }

    @Override
    public List<Team> getTeamsByEvent(long eventId) {
        List<Team> teams = teamRepository.findByEventId(eventId);
        if (teams.isEmpty()) {
            return null;
        }
        return teams;
    }

    @Override
    public List<TeamAndPointsDto> getTeamsAndPointsListByEventId(long eventId) {
        try {
            log.debug("getTeamAndPointsListByEventId start:");
            List<Team> teams = teamRepository.findByEventId(eventId);
            List<TeamAndPointsDto> teamAndPointsDtoList;

            if (teams.isEmpty()) {
                return null;
            }

            teamAndPointsDtoList = teams.stream()
                    .map(team -> TeamAndPointsDto.builder()
                            .team(team)
                            .totalPoints(getPointsByTeam(team))
                            .build())
                    .sorted(Comparator.comparingInt(TeamAndPointsDto::getTotalPoints).reversed())
                    .collect(Collectors.toList());

            return teamAndPointsDtoList;
        } catch (Exception e) {
            log.error("getTeamAndPointsListByEventId Error");
            log.error(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
    }

    @Override
    public OverviewTeamDto getTeamAndPointsByTeam(Team team) {
        try {
            log.debug("getOverviewTeamByTeam start");
            List<Athlete> athletes = new ArrayList<>(Arrays.asList(team.getAthlete1(), team.getAthlete2(), team.getAthlete3()));
            List<AthleteAndPointsDto> athleteAndPointsList = new ArrayList<>();
            for (Athlete athlete : athletes) {
                AthleteAndPointsDto athleteAndPoints = AthleteAndPointsDto.builder()
                        .athlete(athlete)
                        .points(resultRepository.findPointsByAthleteIdAndEventId(athlete.getId(), team.getEvent().getId()).orElse(0))
                        .build();
                athleteAndPointsList.add(athleteAndPoints);
            }
            OverviewTeamDto overviewTeamDto = OverviewTeamDto.builder()
                    .team(team)
                    .athleteAndPointsList(athleteAndPointsList)
                    .build();
            overviewTeamDto.calculateTotalPoints();
            return overviewTeamDto;
        } catch (Exception e) {
            log.error("getOverviewTeamByTeam Error");
            log.error(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
    }

    @Override
    public Team getTeamByEventAndUser(Event event) {
        try {
            log.debug("getTeamByEventIdAndUserId start[1]:");
            User currentUser = userService.getCurrentUser();
            long currentUserId = currentUser.getId();
            Team team = teamRepository.findByEventIdAndUserId(event.getId(), currentUserId)
                    .orElseThrow(() -> new IllegalArgumentException("Team not found with event ID " + event.getId() + " and user ID " + currentUserId));
            log.debug("Team retrieved: " + team);
            return team;
        } catch (Exception e) {
            log.error("getTeamByEventIdAndUserId Error");
            log.error(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
    }

    @Override
    @Transactional
    public Team addNewTeam(CreateTeamRequest request) {
        try {
            log.debug("addNewTeam start[1]:");
            log.debug("eventID - " + request.getEventId());
            Event event = eventRepository.findById(request.getEventId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid event id"));
            User currentUser = userService.getCurrentUser();

            if (teamRepository.existsTeamByEventIdAndUserId(request.getEventId(), currentUser.getId())) {
                throw new IllegalArgumentException("User is already registered for this tournament");
            }

            List<Athlete> athleteList = validateAthleteIds(request.getSelectedAthleteIds());

            Team team = Team.builder()
                    .name(request.getTeamName())
                    .event(event)
                    .user(currentUser)
                    .athlete1(athleteList.get(0))
                    .athlete2(athleteList.get(1))
                    .athlete3(athleteList.get(2))
                    .build();
            log.debug("addNewTeam team added [2]: " + team);
            return teamRepository.save(team);
        } catch (Exception e) {
            log.error("addNewTeam Error");
            log.error(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
    }

    public int getPointsByTeam(Team team) {
        int totalPoints = 0;
        totalPoints += resultRepository.findPointsByAthleteIdAndEventId(
                team.getAthlete1().getId(), team.getEvent().getId()).orElse(0);
        totalPoints += resultRepository.findPointsByAthleteIdAndEventId(
                team.getAthlete2().getId(), team.getEvent().getId()).orElse(0);
        totalPoints += resultRepository.findPointsByAthleteIdAndEventId(
                team.getAthlete3().getId(), team.getEvent().getId()).orElse(0);
        return totalPoints;
    }

    private List<Athlete> validateAthleteIds(List<Long> athleteIds) {
        if (athleteIds.size() != 3) {
            throw new IllegalArgumentException("Exactly 3 sportsmen are required");
        }
        List<Athlete> athletes = new ArrayList<>();
        athletes.add(athleteRepository.findById(athleteIds.get(0))
                .orElseThrow(() -> new IllegalArgumentException("Invalid athlete id")));
        athletes.add(athleteRepository.findById(athleteIds.get(1))
                .orElseThrow(() -> new IllegalArgumentException("Invalid athlete id")));
        athletes.add(athleteRepository.findById(athleteIds.get(2))
                .orElseThrow(() -> new IllegalArgumentException("Invalid athlete id")));
        return athletes;
    }
}
