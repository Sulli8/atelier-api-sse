package com.example.atelier_api_sullivan_sextius.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CountryDTO {

    @JsonProperty("code")
    private String code;
    @JsonProperty("picture")
    private String picture;

    public CountryDTO() {}

    public CountryDTO(String code, String picture) {
        this.code = code;
        this.picture = picture;
    }
}
