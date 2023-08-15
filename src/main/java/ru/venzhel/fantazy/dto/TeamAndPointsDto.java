package ru.venzhel.fantazy.dto;

import lombok.Builder;
import lombok.Data;
import ru.venzhel.fantazy.model.Team;

@Data
@Builder
public class TeamAndPointsDto {
    private Team team;
    private int totalPoints;
}
