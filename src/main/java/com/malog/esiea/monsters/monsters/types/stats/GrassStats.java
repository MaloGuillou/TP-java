package com.malog.esiea.monsters.monsters.types.stats;

import com.malog.esiea.monsters.game.event.Event;
import com.malog.esiea.monsters.game.event.MonsterHealedEvent;
import com.malog.esiea.monsters.game.event.StateEndedEvent;
import com.malog.esiea.monsters.helpers.Randoms;
import com.malog.esiea.monsters.monsters.Monster;
import com.malog.esiea.monsters.monsters.types.Type;
import com.malog.esiea.monsters.terrains.Terrain;

import java.util.ArrayList;

public class GrassStats extends NatureStats{
    private final int heal_probability;

    public GrassStats(Type type, int heal_probability) {
        super(type);
        this.heal_probability = heal_probability;
    }

    private boolean will_it_heal(){
        int rnd = Randoms.get_random_int_in_range(0, 100);
        return rnd < this.heal_probability;
    }

    @Override
    public ArrayList<Event> special_trigger(Monster self, Monster opponent, Terrain terrain) {
        ArrayList<Event> events = new ArrayList<>();
        if(self.get_current_state() != null){
            if(this.will_it_heal()){
                events.add(new StateEndedEvent(self.get_current_state(), self));
                self.remove_state();
            }
        }
        return events;
    }

    @Override
    public ArrayList<Event> start_of_round_trigger(Monster self, Monster opponent, Terrain terrain) {
        return super.start_of_round_trigger(self, opponent, terrain);
    }

    @Override
    public ArrayList<Event> end_of_round_trigger(Monster self, Monster opponent, Terrain terrain) {
        return super.end_of_round_trigger(self, opponent, terrain);
    }
}
