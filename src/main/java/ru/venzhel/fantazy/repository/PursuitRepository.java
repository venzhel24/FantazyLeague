package ru.venzhel.fantazy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.venzhel.fantazy.model.Pursuit;

import java.util.List;

public interface PursuitRepository extends JpaRepository<Pursuit, Long> {
    List<Pursuit> findByRaceId(long RaceId);
}
