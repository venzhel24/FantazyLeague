package ru.venzhel.fantazy.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.venzhel.fantazy.dto.*;
import ru.venzhel.fantazy.model.*;
import ru.venzhel.fantazy.service.ResultsService;
import ru.venzhel.fantazy.service.impl.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/guest")
@RequiredArgsConstructor
@Log4j
public class GuestController {
    private final ResultsServiceImpl resultsService;
    private final TeamServiceImpl teamService;
    private final RaceServiceImpl raceService;
    private final AthleteServiceImpl athleteService;
    private final EventServiceImpl eventService;

    @GetMapping("/last-event")
    public ResponseEntity<Event> getLastEvent() {
        Event lastEvent = eventService.getLastEvent();
        return ResponseEntity.ok(lastEvent);
    }

    @GetMapping("/current-event")
    public ResponseEntity<Event> getCurrentEvent() {
        Event currentEvent = eventService.getCurrentEvent();
        return ResponseEntity.ok(currentEvent);
    }

    @GetMapping("/next-event")
    public ResponseEntity<Event> getNextEvent() {
        Event nextEvent = eventService.getNextEvent();
        return ResponseEntity.ok(nextEvent);
    }


    @GetMapping("/results/{id}")
    public ResponseEntity<ResultsResponse> checkResults(@PathVariable("id") long raceId) {
        List<Result> list = resultsService.getAllResultsByRace(raceId);
        Race race = raceService.getById(raceId);
        ResultsResponse response = new ResultsResponse(list, race);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/leaderboard/{id}")
    public ResponseEntity<TeamsAndPointsResponse> getStandingsByEvent(@PathVariable("id") long eventId) {
        List<TeamAndPointsDto> teamAndPointsDtoList = teamService.getTeamsAndPointsListByEventId(eventId);
        TeamsAndPointsResponse response = new TeamsAndPointsResponse(teamAndPointsDtoList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/team-overview/{id}")
    public ResponseEntity<OverviewTeamResponse> overviewTeam(@PathVariable("id") long teamId) {
        Team team = teamService.getTeamById(teamId);
        OverviewTeamDto overviewTeamDto = teamService.getTeamAndPointsByTeam(team);
        OverviewTeamResponse response = new OverviewTeamResponse(overviewTeamDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/races")
    public ResponseEntity<List<Race>> findAllRaces() {
        List<Race> races = raceService.getAll();
        return ResponseEntity.ok(races);
    }

    @GetMapping("/event-races/{id}")
    public ResponseEntity<List<Race>> getRacesByEventId(@PathVariable("id") long eventId) {
        List<Race> races = raceService.getByEventId(eventId);
        return ResponseEntity.ok(races);
    }

    @GetMapping("/athletes")
    public ResponseEntity<?> getAthletes(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        if (page != null && size != null) {
            // Если параметры page и size переданы, выполняем пагинацию
            Page<Athlete> athletes = athleteService.getAll(page, size);
            return new ResponseEntity<>(athletes, HttpStatus.OK);
        } else {
            // Если параметры не переданы, возвращаем все записи
            List<Athlete> athletes = athleteService.getAll();
            return ResponseEntity.ok(athletes);
        }
    }

    @GetMapping("/events")
    public ResponseEntity<List<Event>> allEvents() {
        List<Event> eventList = eventService.getAll();
        return ResponseEntity.ok(eventList);
    }
}
