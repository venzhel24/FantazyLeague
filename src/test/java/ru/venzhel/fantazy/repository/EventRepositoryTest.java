package ru.venzhel.fantazy.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.venzhel.fantazy.model.Event;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
class EventRepositoryTest {

    @Autowired
    EventRepository eventRepository;

    @Test
    public void testFindCurrentEvent_EventFound() {
        LocalDate today = LocalDate.now();
        Optional<Event> result = eventRepository.findCurrentEvent(today);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(2L, result.get().getId());
        assertEquals("CurrentEventCity", result.get().getCity());
    }

    @Test
    public void testFindCurrentEvent_EventNotFound() {
        LocalDate fakeToday = LocalDate.now().plusYears(10);
        Optional<Event> result = eventRepository.findCurrentEvent(fakeToday);

        assertFalse(result.isPresent());
    }


    @Test
    public void testFindNextEvents_EventFound() {
        LocalDate today = LocalDate.now();
        List<Event> result = eventRepository.findNextEvents(today);

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(3L, result.get(0).getId());
        assertEquals("FutureEventCity", result.get(0).getCity());
    }

    @Test
    public void testFindNextEvents_EventNotFound() {
        LocalDate fakeToday = LocalDate.now().plusYears(10);
        List<Event> result = eventRepository.findNextEvents(fakeToday);

        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindLastEvents_EventFound() {
        LocalDate today = LocalDate.now();
        List<Event> result = eventRepository.findLastEvents(today);

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1L, result.get(0).getId());
        assertEquals("FinishedEventCity", result.get(0).getCity());
    }

    @Test
    public void testFindLastEvents_EventNotFound() {
        LocalDate fakeToday = LocalDate.now().minusYears(100);
        List<Event> result = eventRepository.findLastEvents(fakeToday);

        assertTrue(result.isEmpty());
    }


    @Test
    public void testFindByCityAndStartDateYear_EventFound() {
        String city = "FinishedEventCity";
        int year = LocalDate.now().getYear();

        Optional<Event> result = eventRepository.findByCityAndYear(city, year);

        assertTrue(result.isPresent());
        assertEquals(city, result.get().getCity());
        assertEquals(year, result.get().getStartDate().getYear());
    }

    @Test
    public void testFindByCityAndStartDateYear_EventNotFound() {
        // Mock data
        String city = "OBERHOFQWE";
        int year = 20502;

        Optional<Event> result = eventRepository.findByCityAndYear(city, year);

        assertFalse(result.isPresent());
    }

}