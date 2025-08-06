package com.example.atelier_api_sullivan_sextius.wrapper;

import com.example.atelier_api_sullivan_sextius.dto.PlayerDTO;
import com.example.atelier_api_sullivan_sextius.entity.Player;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data

@AllArgsConstructor
public class PlayersResponse {
    private List<PlayerDTO> players;
    public PlayersResponse() {}
}
