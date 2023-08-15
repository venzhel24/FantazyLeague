package ru.venzhel.fantazy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.venzhel.fantazy.model.Individual;

import java.util.List;

public interface IndividualRepository extends JpaRepository<Individual, Long> {
    List<Individual> findByRaceId(long raceId);
}
