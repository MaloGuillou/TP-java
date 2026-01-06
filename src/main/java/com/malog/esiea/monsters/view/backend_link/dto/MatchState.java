package com.malog.esiea.monsters.view.backend_link.dto;

import com.malog.esiea.monsters.game.Player;
import com.malog.esiea.monsters.terrains.Terrain;

public class MatchState {
    private final Player player_1; // Must be the player that receives the information
    private final Player player_2; // Must be the opponent
    private final Terrain terrain;

    public MatchState(Player player_1, Player player_2, Terrain terrain) {
        this.player_1 = player_1;
        this.player_2 = player_2;
        this.terrain = terrain;
    }

    public Player getPlayer_1() {
        return player_1;
    }

    public Player getPlayer_2() {
        return player_2;
    }

    public Terrain getTerrain() {
        return terrain;
    }
}
