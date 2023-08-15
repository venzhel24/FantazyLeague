package ru.venzhel.fantazy.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import ru.venzhel.fantazy.model.Event;
import ru.venzhel.fantazy.repository.EventRepository;
import ru.venzhel.fantazy.service.EventService;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    @Override
    public List<Event> getAll() {
        List<Event> events = eventRepository.findAll();
        if (events.isEmpty()) {
            return null;
        }
        return events;
    }

    @Override
    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public void deleteById(long id) {
        eventRepository.deleteById(id);
    }

    @Override
    public Event getById(long id) {
        return eventRepository.findById(id).orElse(null);
    }

    @Override
    public Event getCurrentEvent() {
        try {
            log.debug("getCurrentTournament start[1]:");
            List<Event> events = eventRepository.findAll();
            Event event = events.stream()
                    .filter(t -> {
                        LocalDate startDate = t.getStartDate();
                        LocalDate endDate = t.getEndDate();
                        return !LocalDate.now().isBefore(startDate) && !LocalDate.now().isAfter(endDate);
                    })
                    .findFirst()
                    .orElse(null);
            log.debug("Current tournament [2]: " + event);
            return event;
        } catch (Exception e) {
            log.error("getCurrentTournament Error");
            log.error(e.getClass().getName() + ":" + e.getMessage());
            return null;
        }
    }

    @Override
    public Event getNextEvent() {
        try {
            log.debug("getNextEvent start[1]:");
            LocalDate today = LocalDate.now();
            List<Event> events = eventRepository.findNextEvents(today);
            if (events.isEmpty()) {
                return null;
            }
            Event event = events.get(0);
            log.debug("Next tournament [2]: " + event);
            return event;
        } catch (Exception e) {
            log.error("getNextEvent Error");
            log.error(e.getClass().getName() + ":" + e.getMessage());
            return null;
        }
    }

    @Override
    public Event getLastEvent() {
        try {
            log.debug("getLastEvent start[1]:");
            LocalDate today = LocalDate.now();
            List<Event> events = eventRepository.findLastEvents(today);
            if (events.isEmpty()) {
                return null;
            }
            Event event = events.get(0);
            log.debug("Last tournament [2]: " + event);
            return event;
        } catch (Exception e) {
            log.error("getLastEvent Error");
            log.error(e.getClass().getName() + ":" + e.getMessage());
            return null;
        }
    }
}