package ru.venzhel.fantazy.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.venzhel.fantazy.dto.*;
import ru.venzhel.fantazy.model.Event;
import ru.venzhel.fantazy.model.Team;
import ru.venzhel.fantazy.service.impl.EventServiceImpl;
import ru.venzhel.fantazy.service.impl.TeamServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Log4j
public class UserController {

    private final EventServiceImpl eventService;
    private final TeamServiceImpl teamService;

    @PostMapping("/create-team")
    public ResponseEntity<CreateTeamResponse> createTeam(@RequestBody CreateTeamRequest request) {
        Team team = teamService.addNewTeam(request);
        Event event = eventService.getById(request.getEventId());
        CreateTeamResponse response = new CreateTeamResponse(event, team);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/team/{id}")
    public ResponseEntity<TeamResponse> getTeam(@PathVariable("id") long eventId) {
        Event event = eventService.getById(eventId);
        Team team = teamService.getTeamByEventAndUser(event);
        OverviewTeamDto overviewTeamDto = teamService.getTeamAndPointsByTeam(team);
        TeamResponse response = new TeamResponse(overviewTeamDto, event);
        return ResponseEntity.ok(response);
    }
}
