package com.malog.esiea.monsters.states;

import com.malog.esiea.monsters.game.event.Event;
import com.malog.esiea.monsters.monsters.Monster;
import com.malog.esiea.monsters.terrains.Terrain;

public abstract class State {

    public abstract Event update_state(Monster self, Monster opponent, Terrain terrain);
}
