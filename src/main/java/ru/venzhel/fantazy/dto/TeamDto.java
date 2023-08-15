package ru.venzhel.fantazy.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TeamDto {
    private String name;
    private long eventId;
    private List<Long> athleteIds;
}
