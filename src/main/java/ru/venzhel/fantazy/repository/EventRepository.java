package ru.venzhel.fantazy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.venzhel.fantazy.model.Event;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("SELECT e FROM Event e WHERE e.city = :city AND YEAR(e.startDate) = :year")
    Optional<Event> findByCityAndYear(String city, int year);
    @Query("SELECT e FROM Event e WHERE :today BETWEEN e.startDate AND e.endDate")
    Optional<Event> findCurrentEvent(@Param("today") LocalDate today);

    @Query("SELECT e FROM Event e WHERE e.startDate > :today ORDER BY e.startDate ASC")
    List<Event> findNextEvents(@Param("today") LocalDate today);

    @Query("SELECT e FROM Event e WHERE e.endDate < :today ORDER BY e.endDate DESC")
    List<Event> findLastEvents(@Param("today") LocalDate today);
}
