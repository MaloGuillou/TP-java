package com.malog.esiea.monsters.monsters.types.stats;

import com.malog.esiea.monsters.game.event.Event;
import com.malog.esiea.monsters.game.event.MonsterHealedEvent;
import com.malog.esiea.monsters.monsters.Monster;
import com.malog.esiea.monsters.monsters.types.Type;
import com.malog.esiea.monsters.states.terrain.FloodedState;
import com.malog.esiea.monsters.terrains.Terrain;

import java.util.ArrayList;

public class NatureStats extends TypeStats{

    public NatureStats() {
        super(Type.NATURE);
    }

    public NatureStats(Type custom) {
        super(custom);
    }

    @Override
    public ArrayList<Event> special_trigger(Monster self, Monster opponent, Terrain terrain) {
        return new ArrayList<>();
    }

    @Override
    public ArrayList<Event> start_of_round_trigger(Monster self, Monster opponent, Terrain terrain) {
        ArrayList<Event> events = new ArrayList<>();
        if( terrain.getState() != null || terrain.getState() instanceof FloodedState){
            int heal_amount = self.getMaxHP()/20 == 0 ? 1 : self.getMaxHP()/20;

            events.add(new MonsterHealedEvent(self, self.heal(heal_amount)));
        }
        return events;
    }

    @Override
    public ArrayList<Event> end_of_round_trigger(Monster self, Monster opponent, Terrain terrain) {
        return new ArrayList<>();
    }

    @Override
    public TypeStats clone() {
        return new NatureStats();
    }
}
