package com.gamematch.GameMatch.service;

import com.gamematch.GameMatch.model.Player;
import com.gamematch.GameMatch.model.Match;
import org.springframework.stereotype.Service;

import java.lang.StackWalker.Option;
import java.util.*;

@Service
public class MatchMakingService {
    private final Map<UUID, Player> playersMap = new HashMap<>();
    private final Set<UUID> queue = new LinkedHashSet<>();
    private final List<Match> matches = new ArrayList<>();

    private static final int MaxMMRDifference = 100;

    public Player registerPlayer(String username, int mmr) {

        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("username can't be blank");
        }
        if (mmr < 0 || mmr > 10000) {
            throw new IllegalArgumentException("MMR must be within 0-10000 points");
        }

        Player p = new Player(username, mmr);
        playersMap.put(p.getId(), p);

        return p;
    }

    public List<Player> getAllPlayers() {
        return new ArrayList<>(playersMap.values());
    }

    public boolean joinQueue(UUID playerId) {
        if (!playersMap.containsKey(playerId)) {
            throw new IllegalArgumentException("who this?");
        }

        return queue.add(playerId);
    }

    public boolean leaveQueue(UUID playerId) {
        return queue.remove(playerId);
    }

    public List<Player> getQueuedPlayers() {
        List<Player> queuedPlayers = new ArrayList<>();

        for (UUID id : queue) {
            queuedPlayers.add(playersMap.get(id));
        }

        return queuedPlayers;
    }

    public Optional<Match> createMatch() {
        List<Player> queuedPlayers = getQueuedPlayers();

        Player chad1 = null;
        Player chad2 = null;

        int smallestDiff = Integer.MAX_VALUE;

        for (int i = 0; i < queuedPlayers.size(); i++) {
            for (int j = i + 1; j < queuedPlayers.size(); j++) {

                Player p1 = queuedPlayers.get(i);
                Player p2 = queuedPlayers.get(j);

                int difference = Math.abs(p1.getMmr() - p2.getMmr());

                if (difference <= MaxMMRDifference && difference <= smallestDiff) {
                    smallestDiff = difference;
                    chad1 = p1;
                    chad2 = p2;
                }
            }
        }

        if (chad1 == null) {
            return Optional.empty();
        }

        Match match = new Match(chad1, chad2);

        queue.remove(chad1.getId());
        queue.remove(chad2.getId());

        matches.add(match);

        return Optional.of(match);

    }

    public List<Match> getAllMatches() {
        return new ArrayList<>(matches);
    }

    public Player getPlayer(UUID playerId) {
        Player player = playersMap.get(playerId);

        if (player == null) {
            throw new IllegalArgumentException("Player does not exist");
        }

        return player;
    }

}
