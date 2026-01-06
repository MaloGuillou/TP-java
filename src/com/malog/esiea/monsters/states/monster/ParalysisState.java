package com.malog.esiea.monsters.states.monster;

import com.malog.esiea.monsters.game.event.Event;
import com.malog.esiea.monsters.game.event.StateEndedEvent;
import com.malog.esiea.monsters.helpers.Randoms;
import com.malog.esiea.monsters.monsters.Monster;
import com.malog.esiea.monsters.terrains.Terrain;

public class ParalysisState extends MonsterState {
    private int time_spent;

    public ParalysisState(){
        time_spent = 1;
    }

    @Override
    public Event update_state(Monster self, Monster opponent, Terrain terrain) {
        int rnd = Randoms.get_random_int_in_range(1, 6);
        if(rnd <= this.time_spent){
            self.remove_state();
            return new StateEndedEvent(this, self);
        }
        time_spent ++;
        return null;
    }

    @Override
    public String toString(){
        return "Paralysis";
    }
}
