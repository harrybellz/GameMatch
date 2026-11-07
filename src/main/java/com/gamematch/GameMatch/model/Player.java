package com.gamematch.GameMatch.model;

import java.util.UUID;

public class Player {

    private final UUID id;
    private final String username;
    private int mmr; // ranked points
    private int wins;
    private int losses;

    public Player(String username, int mmr) {
        this.id = UUID.randomUUID();
        this.username = username;
        this.mmr = mmr;
        this.wins = 0;
        this.losses = 0;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public int getMmr() {
        return mmr;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public void updateMmr(int amount) {
        this.mmr += amount;
    }

    public void recordWin() {
        wins++;
        updateMmr(25);
    }

    public void recordLoss() {
        losses++;
        updateMmr(-25);
    }
}
