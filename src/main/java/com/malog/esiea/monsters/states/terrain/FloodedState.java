package com.malog.esiea.monsters.states.terrain;

import com.malog.esiea.monsters.game.event.Event;
import com.malog.esiea.monsters.game.event.TerrainStateEndedEvent;
import com.malog.esiea.monsters.helpers.Randoms;
import com.malog.esiea.monsters.monsters.Monster;
import com.malog.esiea.monsters.terrains.Terrain;

public class FloodedState extends TerrainState {
    private final int falling_probability;
    private final int duration;
    private int time_spent;
    private final Monster monster_that_started_it;

    public FloodedState(int falling_probability, Monster monster){
        this.falling_probability = falling_probability;
        monster_that_started_it = monster;
        this.duration = Randoms.get_random_int_in_range(1, 3);
        this.time_spent = 1;
    }

    @Override
    public Event update_state(Monster self, Monster opponent, Terrain terrain) {
        if(this.time_spent == this.duration){
            terrain.removeState();
            return new TerrainStateEndedEvent(this,  terrain);
        }
        this.time_spent ++;
        return null;
    }

    public boolean will_it_fall(){
        int rnd = Randoms.get_random_int_in_range(1, 100);
        return rnd < this.falling_probability;
    }

    public Monster getMonsterThatStartedIt(){
        return this.monster_that_started_it;
    }

    @Override
    public String toString(){
        return "Flooded";
    }
}
