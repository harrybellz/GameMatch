package com.gamematch.GameMatch.controller;

import com.gamematch.GameMatch.model.Match;
import com.gamematch.GameMatch.model.Player;
import com.gamematch.GameMatch.service.MatchMakingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class MatchmakingController {

    private final MatchMakingService service;

    public MatchmakingController(MatchMakingService service) {
        this.service = service;
    }

    public record CreatePlayerRequest(
            @jakarta.validation.constraints.NotBlank(message = "Username cannot be empty") String username,

            @jakarta.validation.constraints.Min(value = 0, message = "MMR must be at least 0") @jakarta.validation.constraints.Max(value = 5000, message = "MMR cannot exceed 5000") int mmr) {
    }

    @PostMapping("/players")
    @ResponseStatus(HttpStatus.CREATED)
    public Player registerPlayer(@jakarta.validation.Valid @RequestBody CreatePlayerRequest request) {
        return service.registerPlayer(request.username(), request.mmr());
    }

    @GetMapping("/players")
    public List<Player> getPlayers() {
        return service.getAllPlayers();
    }

    @PostMapping("/queue/{playerId}")
    public Map<String, String> joinQueue(@PathVariable UUID playerId) {
        boolean added = service.joinQueue(playerId);

        return Map.of(
                "message",
                added ? "Player joined the queue" : "Player is already in the queue");
    }

    @DeleteMapping("/queue/{playerId}")
    public Map<String, String> leaveQueue(@PathVariable UUID playerId) {
        boolean removed = service.leaveQueue(playerId);

        return Map.of(
                "message",
                removed ? "Player left the queue" : "Player was not in the queue");
    }

    @GetMapping("/queue")
    public List<Player> getQueue() {
        return service.getQueuedPlayers();
    }

    @PostMapping("/matches/create")
    public Match createMatch() {
        return service.createMatch().orElseThrow(() -> new ResponseStatusException(
                HttpStatus.CONFLICT,
                "No compatible players are currently available"));
    }

    @GetMapping("/matches")
    public List<Match> getMatches() {
        return service.getAllMatches();
    }
}
