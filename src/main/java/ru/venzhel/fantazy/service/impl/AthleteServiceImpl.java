package ru.venzhel.fantazy.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.venzhel.fantazy.model.Athlete;
import ru.venzhel.fantazy.repository.AthleteRepository;
import ru.venzhel.fantazy.service.AthleteService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AthleteServiceImpl  implements AthleteService {
    private final AthleteRepository athleteRepository;

    @Override
    public List<Athlete> getAll() {
        List<Athlete> athletes = athleteRepository.findAll();
        if (athletes.isEmpty()) {
            return null;
        }
        return athletes;
    }
}
