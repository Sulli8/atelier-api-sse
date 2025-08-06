package com.example.atelier_api_sullivan_sextius.entity;

import com.example.atelier_api_sullivan_sextius.dto.CountryDTO;
import com.example.atelier_api_sullivan_sextius.dto.StatsDataDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class Player {
    private Long id;
    @JsonProperty("firstname")
    private String firstName;
    @JsonProperty("lastname")
    private String lastName;
    @JsonProperty("sex")
    private String sex;
    @JsonProperty("shortname")
    private String shortName;
    @JsonProperty("picture")
    private String picture;
    @JsonProperty("country")
    private Country country;
    @JsonProperty("data")
    private StatsData data;

    public Player() {

    }
    public static Country convertCountryDTOToCountry(CountryDTO dto) {
            if (dto == null) {
                return null;
            }
            return new Country(dto.getCode(), dto.getPicture());
        }
    public static StatsData convertStatsDataDTOToStatsData(StatsDataDTO statsDataDTO) {
        if (statsDataDTO == null) {
            return null;
        }
        return StatsData.builder()
                .rank(statsDataDTO.getRank())
                .points(statsDataDTO.getPoints())
                .weight(statsDataDTO.getWeight())
                .height(statsDataDTO.getHeight())
                .age(statsDataDTO.getAge())
                .last(statsDataDTO.getLast())
                .build();
    }

}
