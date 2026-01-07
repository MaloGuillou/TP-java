package com.malog.esiea.monsters.monsters.types.stats;

import com.malog.esiea.monsters.game.event.Event;
import com.malog.esiea.monsters.game.event.NewStateAppliedEvent;
import com.malog.esiea.monsters.helpers.Randoms;
import com.malog.esiea.monsters.monsters.Monster;
import com.malog.esiea.monsters.states.State;
import com.malog.esiea.monsters.states.monster.MonsterState;
import com.malog.esiea.monsters.states.monster.UndergroundState;
import com.malog.esiea.monsters.monsters.types.Type;
import com.malog.esiea.monsters.terrains.Terrain;

import java.util.ArrayList;

public class EarthStats extends TypeStats{
    public EarthStats() {
        super(Type.EARTH);
    }

    @Override
    public ArrayList<Event> special_trigger(Monster self, Monster opponent, Terrain terrain) {
        ArrayList<Event> events = new ArrayList<>();
        if(self.get_current_state() == null){
            int rnd = Randoms.get_random_int_in_range(0, 100);
            if(rnd < 5){
                MonsterState new_state = new UndergroundState();
                self.apply_state(new_state);
                events.add(new NewStateAppliedEvent(new_state, self));
            }
        }
        return events;
    }

    @Override
    public ArrayList<Event> start_of_round_trigger(Monster self, Monster opponent, Terrain terrain) {
        return new ArrayList<>();
    }

    @Override
    public ArrayList<Event> end_of_round_trigger(Monster self, Monster opponent, Terrain terrain) {
        return new ArrayList<>();
    }
}
