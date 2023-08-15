package ru.venzhel.fantazy.service;

import ru.venzhel.fantazy.dto.AthleteAndPointsDto;
import ru.venzhel.fantazy.model.Race;
import ru.venzhel.fantazy.model.Result;

import java.util.List;

public interface ResultsService {
    <T extends Result> List<T> getAllResultsByRace(long race_id);

    List<AthleteAndPointsDto> getPointsByEventAndAthleteIds(long eventId, List<Long> athleteIds);

    void deleteByRace(Race race);
}
