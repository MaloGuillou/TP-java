package com.malog.esiea.monsters.monsters.types.stats;

import com.malog.esiea.monsters.game.event.Event;
import com.malog.esiea.monsters.game.event.NewStateAppliedEvent;
import com.malog.esiea.monsters.helpers.Randoms;
import com.malog.esiea.monsters.monsters.Monster;
import com.malog.esiea.monsters.states.State;
import com.malog.esiea.monsters.states.monster.BurningState;
import com.malog.esiea.monsters.monsters.types.Type;
import com.malog.esiea.monsters.states.monster.MonsterState;
import com.malog.esiea.monsters.terrains.Terrain;

import java.util.ArrayList;

public class FireStats extends TypeStats{
    private final int fire_probability;
    public FireStats(int fire_probability) {
        super(Type.FIRE);
        this.fire_probability = fire_probability;
    }

    private boolean will_it_burn(){
        int rnd = Randoms.get_random_int_in_range(0, 100);
        return rnd < this.fire_probability;
    }

    @Override
    public ArrayList<Event> special_trigger(Monster self, Monster opponent, Terrain terrain) {
        ArrayList<Event> events = new ArrayList<>();

        if(opponent.get_current_state() == null){
            if(this.will_it_burn()){
                MonsterState new_state = new BurningState();
                opponent.apply_state(new_state);
                events.add(new NewStateAppliedEvent(new_state, opponent));
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

    @Override
    public TypeStats clone() {
        return new FireStats(this.fire_probability);
    }
}
