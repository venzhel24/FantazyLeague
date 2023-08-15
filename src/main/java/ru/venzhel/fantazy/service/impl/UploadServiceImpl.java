package ru.venzhel.fantazy.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import ru.venzhel.fantazy.model.*;
import ru.venzhel.fantazy.repository.*;
import ru.venzhel.fantazy.service.UploadService;
import ru.venzhel.fantazy.util.AlgorithmUtil;
import ru.venzhel.fantazy.util.DatesUtil;
import ru.venzhel.fantazy.util.UploadUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j
public class UploadServiceImpl implements UploadService {

    private final EventRepository eventRepository;
    private final IndividualRepository individualRepository;
    private final SprintRepository sprintRepository;
    private final AthleteRepository athleteRepository;
    private final MassStartRepository massStartRepository;
    private final PursuitRepository pursuitRepository;
    private final RaceRepository raceRepository;

    @Override
    public boolean uploadRace(byte[] bytes) throws IOException {
        log.debug("start uploadRace");
        String s = UploadUtil.convertPdfToString(bytes);
        String[] arr = s.split("\\r?\\n");

        // Get race type
        String raceType = parseRaceType(arr[arr.length - 8]);

        // Get race date
        LocalDate raceDate = parseRaceDate(arr[arr.length - 6]);

        // Get race distance
        String raceDistance = arr[arr.length - 8].split("\\s")[1];

        // Get event or create new one
        Event event = getOrCreateEvent(arr);
        log.debug("event - : " + event);

        // Check if race already exist
        if (raceRepository.existsByEventAndRaceType(event, RaceType.valueOf(raceType))) {
            log.debug("Race already exist");
            return false;
        }

        // Fill race object
        Race race = Race.builder()
                .raceType(RaceType.valueOf(raceType))
                .distance(raceDistance)
                .date(raceDate)
                .event(event)
                .build();

        log.debug("race - : " + race);
        raceRepository.save(race);

        if (raceType.equals("INDIVIDUAL")) {
            parseIndividualResults(arr, race, event.getId());
        }

        if (raceType.equals("SPRINT")) {
            parseSprintResults(arr, race, event.getId());
        }

        if (raceType.equals("MASS_START")) {
            parseMassStartResults(arr, race, event.getId());
        }

        if (raceType.equals("PURSUIT")) {
            parsePursuitResults(arr, race, event.getId());
        }

        log.debug("Race successfully uploaded");
        return true;
    }

    private String parseRaceType(String raceLine) {
        String[] parts = raceLine.split("\\s");
        if (parts.length == 4) {
            return parts[2] + "_" + parts[3];
        } else {
            return parts[2];
        }
    }

    private LocalDate parseRaceDate(String raceDateLine) {
        String[] parts = raceDateLine.split("\\s");
        return DatesUtil.getDate(Integer.parseInt(parts[1]), parts[2], Integer.parseInt(parts[3]));
    }

    private void parseIndividualResults(String[] arr, Race race, long eventId) {
        Individual individualResult;
        Athlete athlete;
        for (String str : arr) {
            if (UploadUtil.isNotResultString(str)) continue;
            String[] row = str.split("\\s");
            row = UploadUtil.nameEditIndividual(row);
            String athleteName = row[2];
            if (getAthleteByName(athleteName).isEmpty()) {
                athlete = Athlete.builder()
                        .name(athleteName)
                        .country(row[3])
                        .build();
                athlete = athleteRepository.save(athlete);
            } else {
                athlete = getAthleteByName(athleteName).get();
            }
            int rank = Integer.parseInt(row[0]);
            int fantasyPoints = AlgorithmUtil.calculateFantasyPoints(rank);
            individualResult = Individual.builder()
                    .rank(rank)
                    .bib(Integer.parseInt(row[1]))
                    .proneShooting(Integer.parseInt(row[4]))
                    .standingShooting(Integer.parseInt(row[5]))
                    .proneShooting2(Integer.parseInt(row[6]))
                    .standingShooting2(Integer.parseInt(row[7]))
                    .totalMisses(Integer.parseInt(row[8]))
                    .skiTime(row[9])
                    .resultTime(row[10])
                    .behind(row[11])
                    .race(race)
                    .athlete(athlete)
                    .points(fantasyPoints)
                    .build();
            log.debug("    athlete - " + athlete);
            log.debug("    result - " + individualResult);
            individualRepository.save(individualResult);
        }
    }

    public void parseSprintResults(String[] arr, Race race, long eventId) {
        Sprint sprintResult;
        Athlete athlete;
        for (String str : arr) {
            if (UploadUtil.isNotResultString(str)) continue;

            String[] row = str.split("\\s");
            row = UploadUtil.nameEditSprint(row);
            String athleteName = row[2];
            if (getAthleteByName(athleteName).isEmpty()) {
                athlete = Athlete.builder()
                        .name(athleteName)
                        .country(row[3])
                        .build();
                athlete = athleteRepository.save(athlete);
            } else {
                athlete = getAthleteByName(athleteName).get();
            }
            int rank = Integer.parseInt(row[0]);
            int fantasyPoints = AlgorithmUtil.calculateFantasyPoints(rank);
            sprintResult = Sprint.builder()
                    .rank(rank)
                    .bib(Integer.parseInt(row[1]))
                    .proneShooting(Integer.parseInt(row[4]))
                    .standingShooting(Integer.parseInt(row[5]))
                    .totalMisses(Integer.parseInt(row[6]))
                    .resultTime(row[7])
                    .behind(row[8])
                    .race(race)
                    .athlete(athlete)
                    .points(fantasyPoints)
                    .build();
            log.debug("    sportsman - " + athlete);
            log.debug("    result - " + sprintResult);
            sprintRepository.save(sprintResult);
        }
    }

    private void parseMassStartResults(String[] arr, Race race, long eventId) {
        MassStart massStart;
        Athlete athlete;
        for (String str : arr) {
            if (UploadUtil.isNotResultString(str)) continue;

            String[] row = str.split("\\s");
            row = UploadUtil.nameEditMassStart(row);
            String athleteName = row[2];
            if (getAthleteByName(athleteName).isEmpty()) {
                athlete = Athlete.builder()
                        .name(athleteName)
                        .country(row[3])
                        .build();
                athlete = athleteRepository.save(athlete);
            } else {
                athlete = getAthleteByName(athleteName).get();
            }
            int rank = Integer.parseInt(row[0]);
            int fantasyPoints = AlgorithmUtil.calculateFantasyPoints(rank);
            massStart = MassStart.builder()
                    .rank(rank)
                    .bib(Integer.parseInt(row[1]))
                    .proneShooting(Integer.parseInt(row[4]))
                    .standingShooting(Integer.parseInt(row[5]))
                    .proneShooting2(Integer.parseInt(row[6]))
                    .standingShooting2(Integer.parseInt(row[7]))
                    .totalMisses(Integer.parseInt(row[8]))
                    .behind(row[9])
                    .race(race)
                    .athlete(athlete)
                    .points(fantasyPoints)
                    .build();
            log.debug("    sportsman - " + athlete);
            log.debug("    result - " + massStart);
            massStartRepository.save(massStart);
        }
    }

    private void parsePursuitResults(String[] arr, Race race, long eventId) {
        Pursuit pursuit;
        Athlete athlete;
        for (String str : arr) {
            if (UploadUtil.isNotResultString(str)) continue;
            String[] row = str.split("\\s");
            row = UploadUtil.nameEditPursuit(row);
            String athleteName = row[2];
            if (getAthleteByName(athleteName).isEmpty()) {
                athlete = Athlete.builder()
                        .name(athleteName)
                        .country(row[3])
                        .build();
                athlete = athleteRepository.save(athlete);
            } else {
                athlete = getAthleteByName(athleteName).get();
            }
            int rank = Integer.parseInt(row[0]);
            int fantasyPoints = AlgorithmUtil.calculateFantasyPoints(rank);
            pursuit = Pursuit.builder()
                    .rank(rank)
                    .bib(Integer.parseInt(row[1]))
                    .proneShooting(Integer.parseInt(row[4]))
                    .proneShooting2(Integer.parseInt(row[5]))
                    .standingShooting(Integer.parseInt(row[6]))
                    .standingShooting2(Integer.parseInt(row[7]))
                    .totalMisses(Integer.parseInt(row[8]))
                    .behind(row[9])
                    .race(race)
                    .athlete(athlete)
                    .points(fantasyPoints)
                    .build();
            log.debug("    sportsman - " + athlete);
            log.debug("    result - " + pursuit);
            pursuitRepository.save(pursuit);
        }
    }

    private Event getOrCreateEvent(String[] arr) {
        String[] eventParts = arr[arr.length - 10].split("\\s");
        UploadUtil.eventEdit(eventParts);
        String city = arr[arr.length - 11];
        int year = Integer.parseInt(eventParts[4]);
        Optional<Event> optionalEvent = getEventByCityAndYear(city, year);

        if (optionalEvent.isPresent()) {
            return optionalEvent.get();
        }

        LocalDate[] dates = DatesUtil.getDates(
                Integer.parseInt(eventParts[0]),
                Integer.parseInt(eventParts[2]),
                eventParts[3],
                Integer.parseInt(eventParts[4]));

        Event event = Event.builder()
                .city(city)
                .startDate(dates[0])
                .endDate(dates[1])
                .build();
        return eventRepository.save(event);
    }

    public boolean addEvent(Event event) {
        try {
            eventRepository.save(event);
            return true;
        } catch (Exception e) {
            log.error("addEvent Error");
            log.error(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
    }

    private Optional<Athlete> getAthleteByName(String name) {
        return athleteRepository.findAthleteByName(name);
    }

    public Optional<Event> getEventByCityAndYear(String city, int year) {
        return eventRepository.findByCityAndYear(city, year);
    }
}
