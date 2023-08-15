package ru.venzhel.fantazy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.venzhel.fantazy.model.Athlete;

@Data
@Builder
@AllArgsConstructor
public class AthleteAndPointsDto {
    private Athlete athlete;
    private int points;
}
