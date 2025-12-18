package com.malog.esiea.monsters.game.event;

import com.malog.esiea.monsters.states.State;
import com.malog.esiea.monsters.terrains.Terrain;

public class TerrainStateEndedEvent extends Event {
    private final State state_that_ended;
    private final Terrain terrain;

    public TerrainStateEndedEvent(State state_that_ended, Terrain terrain){
        this.state_that_ended = state_that_ended;
        this.terrain = terrain;
    }
}
