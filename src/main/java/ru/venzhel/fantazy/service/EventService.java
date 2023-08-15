package ru.venzhel.fantazy.service;

import ru.venzhel.fantazy.model.Event;

import java.util.List;

public interface EventService {
    List<Event> getAll();

    Event saveEvent(Event event);

    void deleteById(long id);

    Event getById(long id);

    Event getCurrentEvent();

    Event getNextEvent();

    Event getLastEvent();
}
