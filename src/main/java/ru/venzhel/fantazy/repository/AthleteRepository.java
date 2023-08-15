package ru.venzhel.fantazy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.venzhel.fantazy.model.Athlete;

import java.util.Optional;

public interface AthleteRepository extends JpaRepository<Athlete, Long> {
    Optional<Athlete> findAthleteByName(String name);
}
