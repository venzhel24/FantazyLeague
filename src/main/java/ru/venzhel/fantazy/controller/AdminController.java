package ru.venzhel.fantazy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.venzhel.fantazy.dto.EventRequest;
import ru.venzhel.fantazy.model.Athlete;
import ru.venzhel.fantazy.model.Event;
import ru.venzhel.fantazy.model.Race;
import ru.venzhel.fantazy.model.User;
import ru.venzhel.fantazy.service.impl.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UploadServiceImpl uploadService;
    private final UserServiceImpl userService;
    private final RaceServiceImpl raceService;

    private final EventServiceImpl eventService;
    private final AthleteServiceImpl athleteService;

    @PostMapping("upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please select a file to upload");
        }

        try {
            if (uploadService.uploadRace(file.getBytes())) {
                return ResponseEntity.ok("Successfully uploaded '" + file.getOriginalFilename() + "'");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("File upload '" + file.getOriginalFilename() + "' FAILED");
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Bad file");
        }
    }

    @PostMapping("/add-event")
    public ResponseEntity<String> addEvent(@RequestBody EventRequest eventRequest) {
        Event event = Event.builder()
                .city(eventRequest.getCityName())
                .startDate(eventRequest.getStartDate())
                .endDate(eventRequest.getEndDate())
                .build();

        if (uploadService.addEvent(event)) {
            return ResponseEntity.ok("Event added");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Event adding failed");
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/event-races/{id}")
    public ResponseEntity<List<Race>> getRacesByEvent(@PathVariable("id") long id) {
        List<Race> races = raceService.getByEventId(id);
        return ResponseEntity.ok(races);
    }

    @PostMapping("/race-create")
    public ResponseEntity<Void> createRace(@RequestBody Race race) {
        raceService.saveRace(race);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/race-delete/{id}")
    public ResponseEntity<Void> deleteRace(@PathVariable("id") long raceId) {
        Race race = raceService.getById(raceId);
        raceService.deleteById(race.getId());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/race-update/{id}")
    public ResponseEntity<Void> updateRace(@PathVariable("id") Long id, @RequestBody Race race) {
        race.setId(id);
        raceService.saveRace(race);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/events")
    public ResponseEntity<List<Event>> findAllEvents() {
        List<Event> events = eventService.getAll();
        return ResponseEntity.ok(events);
    }

    @DeleteMapping("/event-delete/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable("id") long id) {
        eventService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/event-update/{id}")
    public ResponseEntity<Void> updateEvent(@PathVariable("id") Long id, @RequestBody Event event) {
        event.setId(id);
        eventService.saveEvent(event);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/athletes")
    public ResponseEntity<List<Athlete>> getAthletes() {
        List<Athlete> athletes = athleteService.getAll();
        return ResponseEntity.ok(athletes);
    }
}
