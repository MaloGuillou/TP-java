package com.malog.esiea.monsters.game.event;

import com.malog.esiea.monsters.states.State;
import com.malog.esiea.monsters.terrains.Terrain;

public class TerrainStateAppliedEvent extends Event {
    private final State state_that_started;
    private final Terrain terrain;

    public TerrainStateAppliedEvent(State state_that_started, Terrain terrain){
        this.state_that_started = state_that_started;
        this.terrain = terrain;
    }
}
