package com.malog.esiea.monsters.monsters.types.stats;

import com.malog.esiea.monsters.game.event.Event;
import com.malog.esiea.monsters.monsters.Monster;
import com.malog.esiea.monsters.monsters.types.Type;
import com.malog.esiea.monsters.terrains.Terrain;

import java.util.ArrayList;

public class NatureStats extends TypeStats{

    public NatureStats(Type type) {
        super(type);
    }

    @Override
    public ArrayList<Event> special_trigger(Monster self, Monster opponent, Terrain terrain) {
        return null;
    }

    @Override
    public ArrayList<Event> start_of_round_trigger(Monster self, Monster opponent, Terrain terrain) {
        self.heal(self.getMaxHP()/20 == 0 ? 1 : self.getMaxHP()/20);
        return null;
    }

    @Override
    public ArrayList<Event> end_of_round_trigger(Monster self, Monster opponent, Terrain terrain) {
        return null;
    }
}
