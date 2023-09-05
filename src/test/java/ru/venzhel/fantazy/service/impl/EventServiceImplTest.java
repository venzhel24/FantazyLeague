package ru.venzhel.fantazy.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.venzhel.fantazy.model.Event;
import ru.venzhel.fantazy.repository.EventRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    @Mock
    EventRepository eventRepository;

    EventServiceImpl eventService;

    @BeforeEach
    public void setup() {
        eventService = new EventServiceImpl(eventRepository);
    }

    @Test
    public void testGetAll_WithEvents_ReturnsList() {
        // Arrange
        List<Event> events = new ArrayList<>();
        Event event1 = new Event();
        event1.setCity("Event 1");
        events.add(event1);

        Event event2 = new Event();
        event2.setCity("Event 2");
        events.add(event2);

        when(eventRepository.findAll()).thenReturn(events);

        // Act
        List<Event> result = eventService.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Event 1", result.get(0).getCity());
        assertEquals("Event 2", result.get(1).getCity());
    }

    @Test
    public void testGetAll_WithNoEvents_ReturnsNull() {
        // Arrange
        List<Event> emptyList = new ArrayList<>();
        when(eventRepository.findAll()).thenReturn(emptyList);

        // Act
        List<Event> result = eventService.getAll();

        // Assert
        assertNull(result);
    }

    @Test
    public void getCurrentEvent_returnsNull_whenNoEventExists() {
        when(eventRepository.findAll()).thenReturn(List.of());
        Event currentEvent = eventService.getCurrentEvent();
        assertNull(currentEvent);
    }

    @Test
    public void getCurrentEvent_returnsNull_whenNoCurrentEventExists() {
        Event event1 = mock(Event.class);
        when(event1.getStartDate()).thenReturn(LocalDate.of(2022, 4, 1));
        when(event1.getEndDate()).thenReturn(LocalDate.of(2022, 4, 10));
        Event event2 = mock(Event.class);
        when(event2.getStartDate()).thenReturn(LocalDate.of(2022, 5, 1));
        when(event2.getEndDate()).thenReturn(LocalDate.of(2022, 5, 10));
        when(eventRepository.findAll()).thenReturn(Arrays.asList(event1, event2));

        Event currentEvent = eventService.getCurrentEvent();

        assertNull(currentEvent);
    }

    @Test
    public void getCurrentEvent_returnsCurrentEvent_whenOneExists() {
        // given
        Event event1 = mock(Event.class);
        when(event1.getStartDate()).thenReturn(LocalDate.now().minusDays(20));
        when(event1.getEndDate()).thenReturn(LocalDate.now().minusDays(15));

        Event event2 = mock(Event.class);
        when(event2.getStartDate()).thenReturn(LocalDate.now().minusDays(1));
        when(event2.getEndDate()).thenReturn(LocalDate.now().plusDays(3));
        when(eventRepository.findAll()).thenReturn(Arrays.asList(event1, event2));

        // act
        Event currentEvent = eventService.getCurrentEvent();

        // assert
        assertEquals(event2, currentEvent);
    }

    @Test
    public void getFutureEvent_returnsNull_whenNoEventExists() {
        //when(eventRepository.findAll()).thenReturn(List.of());
        Event futureEvent = eventService.getNextEvent();
        assertNull(futureEvent);
    }

    @Test
    public void getFutureEvent_returnsNull_whenNoFutureEventExists() {
//        Event event1 = new Event();
//        event1.setStartDate(LocalDate.of(2021, 4, 1));
//        when(eventRepository.findAll()).thenReturn(List.of(event1));
        Event futureEvent = eventService.getNextEvent();
        assertNull(futureEvent);
    }

    @Test
    public void getFutureEvent_returnsClosestFutureEvent_whenMultipleExist() {
        Event event1 = new Event();
        event1.setEndDate(LocalDate.now().plusWeeks(3));

        Event event2 = new Event();
        event2.setEndDate(LocalDate.now().plusWeeks(1));

        Event event3 = new Event();
        event3.setEndDate(LocalDate.now().plusMonths(2));

        when(eventRepository.findNextEvents(LocalDate.now())).thenReturn(Arrays.asList(event2, event1, event3));

        Event futureEvent = eventService.getNextEvent();

        assertEquals(event2, futureEvent);
    }

    @Test
    public void getPastEvent_returnsNull_whenNoEventExists() {
        Event pastEvent = eventService.getLastEvent();
        assertNull(pastEvent);
    }

    @Test
    public void getPastEvent_returnsNull_whenNoPastEventExists() {
        Event pastEvent = eventService.getLastEvent();
        assertNull(pastEvent);
    }

    @Test
    public void getPastEvent_returnsLastPastEvent_whenMultipleExist() {
        Event event1 = new Event();
        event1.setEndDate(LocalDate.now().minusWeeks(3));

        Event event2 = new Event();
        event2.setEndDate(LocalDate.now().minusWeeks(1));

        Event event3 = new Event();
        event3.setEndDate(LocalDate.now().minusMonths(2));

        when(eventRepository.findLastEvents(Mockito.any())).thenReturn(Arrays.asList(event2, event1, event3));

        Event pastEvent = eventService.getLastEvent();

        assertEquals(event2, pastEvent);
    }
}