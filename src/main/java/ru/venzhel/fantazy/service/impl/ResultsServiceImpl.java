package ru.venzhel.fantazy.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import ru.venzhel.fantazy.dto.AthleteAndPointsDto;
import ru.venzhel.fantazy.model.Athlete;
import ru.venzhel.fantazy.model.Race;
import ru.venzhel.fantazy.model.RaceType;
import ru.venzhel.fantazy.model.Result;
import ru.venzhel.fantazy.repository.*;
import ru.venzhel.fantazy.service.ResultsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j
@RequiredArgsConstructor
public class ResultsServiceImpl implements ResultsService {
    private final ResultRepository resultRepository;
    private final IndividualRepository individualRepository;
    private final SprintRepository sprintRepository;
    private final MassStartRepository massStartRepository;
    private final PursuitRepository pursuitRepository;
    private final RaceRepository raceRepository;
    private final AthleteRepository athleteRepository;

    @Override
    public <T extends Result> List<T> getAllResultsByRace(long race_id) {
        try {
            log.debug("getAllResultsByRace start [1]:");
            Race race = raceRepository.findById(race_id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid race id"));
            RaceType race_type = race.getRaceType();
            List<T> results;

            if (race_type.equals(RaceType.MASS_START)) {
                results = (List<T>) massStartRepository.findByRaceId(race_id);
                log.debug("MassStart results retrieved [2]:");
                return results;
            }

            if (race_type.equals(RaceType.PURSUIT)) {
                results = (List<T>) pursuitRepository.findByRaceId(race_id);
                log.debug("Pursuit results retrieved [2]:");
                return results;
            }

            if (race_type.equals(RaceType.INDIVIDUAL)) {
                results = (List<T>) individualRepository.findByRaceId(race_id);
                log.debug("Individual results retrieved [2]:");
                return results;
            }

            if (race_type.equals(RaceType.SPRINT)) {
                results = (List<T>) sprintRepository.findByRaceId(race_id);
                log.debug("Sprint results retrieved [2]:");
                return results;
            }

            throw new IllegalArgumentException("Invalid race type");
        } catch (Exception e) {
            log.error("getAllResultsByRace Error");
            log.error(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<AthleteAndPointsDto> getPointsByEventAndAthleteIds(long eventId, List<Long> athleteIds) {
        try{
            log.debug("getPointsByEventAndAthlete start [1]:");
            List<AthleteAndPointsDto> athleteAndPointsList = new ArrayList<>();

            for(long athleteId : athleteIds) {
                Athlete athlete = athleteRepository.findById(athleteId)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid athlete id"));

                // Get the points earned by the specified athlete in the specified event.
                Optional<Integer> athleteEventPoints = resultRepository.findPointsByAthleteIdAndEventId(athleteId, eventId);
                if (athleteEventPoints.isEmpty()) {
                    athleteEventPoints = Optional.of(0);
                }
                AthleteAndPointsDto athleteAndPoints = AthleteAndPointsDto.builder()
                        .athlete(athlete)
                        .points(athleteEventPoints.get())
                        .build();
                athleteAndPointsList.add(athleteAndPoints);
            }
            log.debug("points retrieved [2]:");
            return athleteAndPointsList;
        } catch (Exception e) {
            log.error("getPointsByEventAndAthlete Error");
            log.error(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteByRace(Race race) {
        resultRepository.deleteAllByRace(race);
    }
}
