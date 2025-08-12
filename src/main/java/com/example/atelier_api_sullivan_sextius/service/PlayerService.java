package com.example.atelier_api_sullivan_sextius.service;

import com.example.atelier_api_sullivan_sextius.dto.PlayerDTO;
import com.example.atelier_api_sullivan_sextius.dto.PlayerStatsDTO;
import com.example.atelier_api_sullivan_sextius.dto.SuccessDTO;
import com.example.atelier_api_sullivan_sextius.exceptions.BadRequestException;
import com.example.atelier_api_sullivan_sextius.exceptions.PlayerNotFoundException;
import com.example.atelier_api_sullivan_sextius.exceptions.ResourceNotFoundException;
import com.example.atelier_api_sullivan_sextius.storage.PlayerStorage;
import com.example.atelier_api_sullivan_sextius.storage.PlayerStorageInterface;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class PlayerService {
    private ObjectMapper mapper;
    private final PlayerStorageInterface playerStorage;

    public PlayerService(ObjectMapper mapper, PlayerStorage playerStorage) {
        this.mapper = mapper;
        this.playerStorage = playerStorage;
    }

    private Long generateId() {
        return System.currentTimeMillis();
    }

    public Map<String, PlayerDTO> getAllPlayers() {
        if (playerStorage.getPlayers() == null) {
            throw new ResourceNotFoundException("No players data provided");
        }
        Map<String, PlayerDTO> players = playerStorage.getPlayers();
        return players;
    }

    public Map<String, PlayerDTO> addPlayer(PlayerDTO dto) {
        if(dto.getId() == null) {
            dto.setId(generateId());
        }
        if (dto.getFirstName() == null || dto.getLastName() == null || dto.getSex() == null) {
            throw new BadRequestException("The first name, last name, and gender are required.");
        }
        if (dto.getCountry() == null || dto.getCountry().getCode() == null) {
            throw new BadRequestException("The country and its code are required.");
        }
        return playerStorage.addOrUpdatePlayer(dto);
    }


    public PlayerDTO getPlayerById(Long id) {
        PlayerDTO player = playerStorage.getPlayerById(id.toString());
        if (player == null) {
            throw new PlayerNotFoundException("The player with id " + id + " does not exist.");
        }
        return playerStorage.getPlayerById(id.toString());
    }


    public List<PlayerDTO>  getPlayersSortedByRank(Map<String, PlayerDTO> players) {
        if (players == null || players.isEmpty()) {
            throw new ResourceNotFoundException("No players data provided");
        }

        return players.values().stream()
                .sorted(Comparator.comparingInt(playerDTO -> playerDTO.getData().getRank()))
                .collect(Collectors.toList());
    }

    public String getTopCountryByWinRatio(Map<String, PlayerDTO> players) {
        if (players == null || players.isEmpty()) {
            throw new ResourceNotFoundException("No players data provided");
        }

        List<PlayerDTO> validPlayers = players.values().stream()
                .filter(p -> p.getCountry() != null && p.getCountry().getCode() != null)
                .collect(Collectors.toList());

        if (validPlayers.isEmpty()) {
            throw new ResourceNotFoundException("No country found with players data");
        }

        return validPlayers.stream()
                .collect(Collectors.groupingBy(playerDTO -> playerDTO.getCountry().getCode()))
                .entrySet()
                .stream()
                .max(Comparator.comparingDouble(entry -> {
                    List<PlayerDTO> playerDTOStats = entry.getValue();

                    double totalMatches = playerDTOStats.stream()
                            .mapToInt(playerDTO -> {
                                if (playerDTO.getData() == null || playerDTO.getData().getLast() == null) return 0;
                                return playerDTO.getData().getLast().size();
                            })
                            .sum();

                    double totalWins = playerDTOStats.stream()
                            .mapToInt(playerDTO -> {
                                if (playerDTO.getData() == null || playerDTO.getData().getLast() == null) return 0;
                                return (int) playerDTO.getData().getLast().stream().filter(result -> result == 1).count();
                            })
                            .sum();

                    return totalMatches == 0 ? 0.0 : totalWins / totalMatches;
                }))
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new ResourceNotFoundException("No valid data to calculate win ratio"));
    }

    public double calculateAverageBmi(Map<String, PlayerDTO> players) {
        if (players == null || players.isEmpty()) {
            throw new ResourceNotFoundException("No players data provided");
        }
        return players.values().stream()
                .mapToDouble(playerDTO -> {
                    if (playerDTO.getData() == null) return 0.0;
                    double heightInMeters = playerDTO.getData().getHeight() / 100.0;
                    double weightInKg = playerDTO.getData().getWeight() / 1000.0;
                    if (heightInMeters == 0) return 0.0;

                    return weightInKg / (heightInMeters * heightInMeters);
                })
                .average()
                .orElse(0.0);
    }

    public double calculateMedianHeight(Map<String, PlayerDTO> players) {
        if (players == null || players.isEmpty()) {
            throw new ResourceNotFoundException("No players data provided");
        }
        List<Integer> heights = players.values().stream()
                .filter(p -> p.getData() != null)
                .map(p -> p.getData().getHeight())
                .sorted()
                .toList();

        int size = heights.size();
        if (size == 0) return 0.0;

        if (size % 2 == 0) {
            return (heights.get(size / 2 - 1) + heights.get(size / 2)) / 2.0;
        } else {
            return heights.get(size / 2);
        }
    }


    public PlayerStatsDTO getStatistics() {
        Map<String, PlayerDTO> players = getAllPlayers();
        if (players == null || players.isEmpty()) {
            throw new ResourceNotFoundException("No players data provided");
        }

        boolean allInvalid = players.values().stream()
                .allMatch(player -> player.getData() == null || player.getCountry() == null);

        if (allInvalid) {
            throw new ResourceNotFoundException("No valid players data (missing data or country)");
        }

        if (players.isEmpty()) {
            return PlayerStatsDTO.builder()
                    .topCountryByWinRatio("Unknown")
                    .averageBmi(0.0)
                    .medianHeight(0.0)
                    .build();
        }

        return PlayerStatsDTO.builder()
                .topCountryByWinRatio(getTopCountryByWinRatio(players))
                .averageBmi(calculateAverageBmi(players))
                .medianHeight(calculateMedianHeight(players))
                .build();
    }

    public Map<String, PlayerDTO>  patchPlayer(PlayerDTO updates) {
        return playerStorage.addOrUpdatePlayer(updates);
    }

    public SuccessDTO deletePlayer(PlayerDTO delete) {
        if (delete == null || delete.getId() == null) {
            throw new IllegalArgumentException("Player Id cannot be null or empty");
        }

        Map<String, PlayerDTO> players = playerStorage.deletePlayer(delete);

        boolean successfullyDeleted = !players.containsKey(delete.getId().toString());
        SuccessDTO successDTO = new SuccessDTO();
        successDTO.setSuccess(successfullyDeleted);

        return successDTO;
    }




}
