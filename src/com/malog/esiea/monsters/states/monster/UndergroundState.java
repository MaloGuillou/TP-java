package com.malog.esiea.monsters.states.monster;

import com.malog.esiea.monsters.game.event.Event;
import com.malog.esiea.monsters.game.event.StateEndedEvent;
import com.malog.esiea.monsters.helpers.Randoms;
import com.malog.esiea.monsters.monsters.Monster;
import com.malog.esiea.monsters.terrains.Terrain;

public class UndergroundState extends MonsterState {
    private final int time_underground;
    private int time_spent;

    public UndergroundState() {
        this.time_underground = Randoms.get_random_int_in_range(1,3);
        this.time_spent = 1;
    }


    @Override
    public Event update_state(Monster self, Monster opponent, Terrain terrain) {
        if(time_underground == time_spent){
            self.remove_state();
            return new StateEndedEvent(this, self);
        }
        time_spent++;
        return null;
    }
}
