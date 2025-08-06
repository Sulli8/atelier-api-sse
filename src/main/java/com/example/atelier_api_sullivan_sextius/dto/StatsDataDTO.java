package com.example.atelier_api_sullivan_sextius.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class StatsDataDTO {
    private int rank;
    private int points;
    private int weight;
    private int height;
    private int age;
    private List<Integer> last;
    public StatsDataDTO() {}
}