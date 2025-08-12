package com.example.atelier_api_sullivan_sextius.storage;

import com.example.atelier_api_sullivan_sextius.dto.PlayerDTO;
import com.example.atelier_api_sullivan_sextius.exceptions.ResourceNotFoundException;
import com.example.atelier_api_sullivan_sextius.wrapper.PlayersResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


@Component
public class PlayerStorage implements PlayerStorageInterface {
    private ObjectMapper mapper = new  ObjectMapper();
    @Value("${players.file}")
    String playersFile;

    Map<String, PlayerDTO> players = new HashMap<>();
    public PlayerStorage(){}
    public String loadJsonFromResources(String filename) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(filename)) {
            if (inputStream == null) {
                throw new IOException("The file '" + filename + "' was not found in /resources");
            }
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
    @PostConstruct
    public void initPlayers() {
        try {
            String json = loadJsonFromResources(playersFile);
            PlayersResponse response = mapper.readValue(json, PlayersResponse.class);

            for (PlayerDTO player : response.getPlayers()) {
                players.put(player.getId().toString(), player);
            }

        } catch (IOException e) {
            throw new RuntimeException("Error occurred while loading players", e);
        }
    }

    public PlayerDTO getPlayerById(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Player id cannot be null or empty");
        }
        PlayerDTO player = players.get(id);
        if (player == null) {
            throw new ResourceNotFoundException("Player with id " + id + " not found");
        }
        return player;
    }

    public Map<String, PlayerDTO> getPlayers() {
        return players;
    }

    public Map<String, PlayerDTO> addOrUpdatePlayer(PlayerDTO player) {
        if (player == null || player.getId() == null) {
            throw new IllegalArgumentException("Player or its ID cannot be null");
        }
        players.put(player.getId().toString(), player);
        return players;
    }

    public Map<String, PlayerDTO> deletePlayer(PlayerDTO playerDTODeleted) {
        if (playerDTODeleted == null || playerDTODeleted.getId() == null) {
            throw new IllegalArgumentException("Player id cannot be null or empty");
        }

        players.remove(playerDTODeleted.getId().toString());
        return players;
    }



}