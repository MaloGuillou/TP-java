package com.malog.esiea.monsters.monsters.types.stats;

import com.malog.esiea.monsters.game.event.Event;
import com.malog.esiea.monsters.game.event.TerrainStateAppliedEvent;
import com.malog.esiea.monsters.helpers.Randoms;
import com.malog.esiea.monsters.monsters.Monster;
import com.malog.esiea.monsters.states.State;
import com.malog.esiea.monsters.states.terrain.FloodedState;
import com.malog.esiea.monsters.monsters.types.Type;
import com.malog.esiea.monsters.terrains.Terrain;

import java.util.ArrayList;

public class WaterStats extends TypeStats{
    private final int flooding_probability;
    private final int falling_probability;


    public WaterStats(int flooding_probability, int falling_probability) {
        super(Type.WATER);
        this.flooding_probability = flooding_probability;
        this.falling_probability = falling_probability;
    }

    @Override
    public ArrayList<Event> special_trigger(Monster self, Monster opponent, Terrain terrain) {
        ArrayList<Event> events = new ArrayList<>();

        if(opponent.get_current_state() == null){
            if(this.will_it_flood()){
                State new_state = new FloodedState(this.falling_probability);
                opponent.apply_state(new_state);
                events.add(new TerrainStateAppliedEvent(new_state, terrain));
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

    private boolean will_it_flood(){
        int rnd = Randoms.get_random_int_in_range(0, 100);
        return rnd < this.flooding_probability;
    }
}
