package ru.venzhel.fantazy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.venzhel.fantazy.model.Event;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamResponse {
    OverviewTeamDto overviewTeamDto;

    Event event;
}
