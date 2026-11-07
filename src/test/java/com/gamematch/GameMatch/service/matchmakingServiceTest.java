
package com.gamematch.GameMatch.service;

import com.gamematch.GameMatch.model.Match;
import com.gamematch.GameMatch.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MatchmakingServiceTest {

    private MatchMakingService service;

    @BeforeEach
    void setUp() {
        service = new MatchMakingService();
    }

    @Test
    void shouldRegisterPlayer() {
        Player player = service.registerPlayer("Alex", 1050);

        assertNotNull(player.getId());
        assertEquals("Alex", player.getUsername());
        assertEquals(1050, player.getMmr());
    }

    @Test
    void shouldRejectInvalidPlayerInformation() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.registerPlayer("", 1050));

        assertThrows(
                IllegalArgumentException.class,
                () -> service.registerPlayer("Alex", -100));
    }

    @Test
    void shouldPreventDuplicateQueueEntries() {
        Player player = service.registerPlayer("Alex", 1050);

        assertTrue(service.joinQueue(player.getId()));
        assertFalse(service.joinQueue(player.getId()));

        assertEquals(1, service.getQueuedPlayers().size());
    }

    @Test
    void shouldMatchPlayersWithinMmrRange() {
        Player alex = service.registerPlayer("Alex", 1050);
        Player jordan = service.registerPlayer("Jordan", 1100);

        service.joinQueue(alex.getId());
        service.joinQueue(jordan.getId());

        Optional<Match> result = service.createMatch();

        assertTrue(result.isPresent());

        Match match = result.get();

        assertEquals("Alex", match.getP1().getUsername());
        assertEquals("Jordan", match.getP2().getUsername());

        assertEquals(0, service.getQueuedPlayers().size());
        assertEquals(1, service.getAllMatches().size());
    }

    @Test
    void shouldNotMatchPlayersOutsideMmrRange() {
        Player alex = service.registerPlayer("Alex", 1000);
        Player jordan = service.registerPlayer("Jordan", 1200);

        service.joinQueue(alex.getId());
        service.joinQueue(jordan.getId());

        Optional<Match> result = service.createMatch();

        assertTrue(result.isEmpty());
        assertEquals(2, service.getQueuedPlayers().size());
        assertEquals(0, service.getAllMatches().size());
    }

    @Test
    void shoudlSelectPlayerWithMostMMR() {
        Player max = service.registerPlayer("max", 1250);
        Player lucas = service.registerPlayer("lucas", 1290);
        Player mike = service.registerPlayer("mike", 1300);
        Player dustin = service.registerPlayer("dustin", 1344);

        service.joinQueue(max.getId());
        service.joinQueue(lucas.getId());
        service.joinQueue(mike.getId());
        service.joinQueue(dustin.getId());

        Optional<Match> result = service.createMatch();

        assertTrue(result.isPresent());

        Match match = result.get();

        assertEquals("lucas", match.getP1().getUsername());
        assertEquals("mike", match.getP2().getUsername());
    }
}
