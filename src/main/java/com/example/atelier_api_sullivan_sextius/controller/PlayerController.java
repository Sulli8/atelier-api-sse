package com.example.atelier_api_sullivan_sextius.controller;

import com.example.atelier_api_sullivan_sextius.dto.PlayerDTO;
import com.example.atelier_api_sullivan_sextius.dto.PlayerStatsDTO;
import com.example.atelier_api_sullivan_sextius.dto.SuccessDTO;
import com.example.atelier_api_sullivan_sextius.service.PlayerService;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Controller
@RequestMapping("/api/players")
public class PlayerController {
    @Autowired
    private PlayerService playerService;


    @GetMapping(value="")
    @Operation(
            summary = "Récupérer tous les joueurs",
            description = "Retourne une liste complète des joueurs avec leurs détails."
    )
    public Map<String, PlayerDTO> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    @GetMapping(value="/{id}")
    @Operation(
            summary = "Récupérer un joueur par son ID",
            description = "Retourne les informations d'un joueur spécifique en fonction de son ID."
    )
    public PlayerDTO getByPlayerId(@PathVariable Long id) {
        return playerService.getPlayerById(id);
    }

    @PostMapping
    @Operation(
            summary = "Ajouter un nouveau joueur",
            description = "Ajoute un joueur avec les informations fournies dans le corps de la requête."
    )
    public Map<String, PlayerDTO> addPlayer(@RequestBody PlayerDTO dto)  {
        Map<String, PlayerDTO>  added = playerService.addPlayer(dto);
        return added;
    }

    @PatchMapping
    @Operation(
            summary = "Mettre à jour partiellement un joueur",
            description = "Met à jour les informations d’un joueur existant en fonction des données fournies."
    )
    public Map<String, PlayerDTO> patchPlayer(@RequestBody PlayerDTO dto)  {
        Map<String, PlayerDTO> updated = playerService.patchPlayer(dto);
        return updated;
    }

    @DeleteMapping
    @Operation(
            summary = "Supprimer un joueur",
            description = "Supprime un joueur existant en fonction des informations fournies."
    )
    public SuccessDTO deletePlayer(@RequestBody PlayerDTO dto)  {
        SuccessDTO succesDTO = playerService.deletePlayer(dto);
        return succesDTO;
    }

    @GetMapping(value="/stats")
    @Operation(
            summary = "Statistiques globales des joueurs",
            description = "Retourne les statistiques générales de tous les joueurs."
    )
    public PlayerStatsDTO getPlayerStats()  {
        return playerService.getStatistics();
    }

    @GetMapping(value="/sorted")
    @Operation(
            summary = "Récupérer les joueurs triés par classement",
            description = "Retourne la liste de tous les joueurs triée en fonction de leur classement."
    )
    public Map<String, PlayerDTO> getPlayersSortedByRank() {
        Map<String, PlayerDTO> players = playerService.getAllPlayers();
        playerService.getPlayersSortedByRank(players);
        return players;
    }
}
