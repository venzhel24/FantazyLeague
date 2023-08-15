package ru.venzhel.fantazy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamsAndPointsResponse {
    List<TeamAndPointsDto> teamAndPointsDtoList;
}
