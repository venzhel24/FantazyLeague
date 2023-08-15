package ru.venzhel.fantazy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.venzhel.fantazy.model.Race;
import ru.venzhel.fantazy.model.Result;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultsResponse {
    List<Result> results;

    Race race;
}
