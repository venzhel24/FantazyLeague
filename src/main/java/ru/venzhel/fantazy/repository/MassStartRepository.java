package ru.venzhel.fantazy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.venzhel.fantazy.model.MassStart;

import java.util.List;

public interface MassStartRepository extends JpaRepository<MassStart, Long> {
    List<MassStart> findByRaceId(long RaceId);
}
