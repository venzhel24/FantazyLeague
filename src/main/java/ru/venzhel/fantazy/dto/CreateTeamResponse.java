package ru.venzhel.fantazy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.venzhel.fantazy.model.Event;
import ru.venzhel.fantazy.model.Team;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTeamResponse {
    Event event;

    Team team;
}
