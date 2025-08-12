package com.example.atelier_api_sullivan_sextius.storage;


import com.example.atelier_api_sullivan_sextius.dto.PlayerDTO;

import java.util.Map;

public interface PlayerStorageInterface {

    PlayerDTO getPlayerById(String id);

    Map<String, PlayerDTO> getPlayers();

    Map<String, PlayerDTO> addOrUpdatePlayer(PlayerDTO player);

    Map<String, PlayerDTO> deletePlayer(PlayerDTO playerDTODeleted);

}
