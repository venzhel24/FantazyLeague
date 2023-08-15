package ru.venzhel.fantazy.dto;

import lombok.Builder;
import lombok.Data;
import ru.venzhel.fantazy.model.Team;

import java.util.List;

@Data
@Builder
public class OverviewTeamDto {
    Team team;
    List<AthleteAndPointsDto> athleteAndPointsList;
    int totalPoints;

    public void calculateTotalPoints() {
        this.totalPoints = 0;
        this.athleteAndPointsList.forEach(athleteAndPoints -> this.totalPoints += athleteAndPoints.getPoints());
    }
}
