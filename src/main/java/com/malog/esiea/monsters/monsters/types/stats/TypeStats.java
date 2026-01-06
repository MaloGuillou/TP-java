package com.malog.esiea.monsters.monsters.types.stats;

import com.malog.esiea.monsters.game.event.Event;
import com.malog.esiea.monsters.monsters.Monster;
import com.malog.esiea.monsters.monsters.types.Type;
import com.malog.esiea.monsters.terrains.Terrain;

import java.util.ArrayList;

public abstract class TypeStats {
    private Type type;

    public TypeStats(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public abstract ArrayList<Event> special_trigger(Monster self, Monster opponent, Terrain terrain);
    public abstract ArrayList<Event> start_of_round_trigger(Monster self, Monster opponent, Terrain terrain);
    public abstract ArrayList<Event> end_of_round_trigger(Monster self, Monster opponent, Terrain terrain);
}
