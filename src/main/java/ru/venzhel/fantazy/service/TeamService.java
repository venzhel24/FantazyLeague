package ru.venzhel.fantazy.service;

import ru.venzhel.fantazy.dto.CreateTeamRequest;
import ru.venzhel.fantazy.dto.OverviewTeamDto;
import ru.venzhel.fantazy.dto.TeamAndPointsDto;
import ru.venzhel.fantazy.model.Event;
import ru.venzhel.fantazy.model.Team;

import java.util.List;

public interface TeamService {
    Team getTeamById(long teamId);

    List<Team> getTeamsByEvent(long eventId);

    List<TeamAndPointsDto> getTeamsAndPointsListByEventId(long eventId);

    OverviewTeamDto getTeamAndPointsByTeam(Team team);

    Team getTeamByEventAndUser(Event event);

    Team addNewTeam(CreateTeamRequest request);
}
