package com.gamematch.GameMatch.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.cglib.core.Local;

public class Match {
    private final UUID matchID;
    private final Player P1;
    private final Player P2;
    private final LocalDateTime creationTime;

    public Match(Player P1, Player P2) {
        this.matchID = UUID.randomUUID();
        this.P1 = P1;
        this.P2 = P2;
        this.creationTime = LocalDateTime.now();
    }

    public UUID getMatchID() {
        return matchID;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public Player getP1() {
        return P1;
    }

    public Player getP2() {
        return P2;
    }

}
