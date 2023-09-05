package ru.venzhel.fantazy.service;

import org.springframework.data.domain.Page;
import ru.venzhel.fantazy.model.Athlete;

import java.util.List;

public interface AthleteService {
    public List<Athlete> getAll();
    Page<Athlete> getAll(int page, int size);
}