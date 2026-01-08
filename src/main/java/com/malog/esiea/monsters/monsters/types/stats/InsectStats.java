package com.malog.esiea.monsters.monsters.types.stats;

import com.malog.esiea.monsters.game.event.Event;
import com.malog.esiea.monsters.game.event.NewStateAppliedEvent;
import com.malog.esiea.monsters.helpers.Randoms;
import com.malog.esiea.monsters.monsters.Monster;
import com.malog.esiea.monsters.states.State;
import com.malog.esiea.monsters.monsters.types.Type;
import com.malog.esiea.monsters.states.monster.MonsterState;
import com.malog.esiea.monsters.states.monster.PoisonState;
import com.malog.esiea.monsters.terrains.Terrain;

import java.util.ArrayList;

public class InsectStats extends NatureStats{
    private int time_since_last_poison;


    public InsectStats() {
        super(Type.INSECT);
        time_since_last_poison = 1;
    }

    private boolean will_it_poison(){
        int poison_probability;
        if(time_since_last_poison == 1){
            poison_probability = 33;
        }else if(time_since_last_poison == 2){
            poison_probability = 50;
        }else{
            poison_probability = 100;
        }

        int rnd = Randoms.get_random_int_in_range(0, 100);
        if(rnd < poison_probability){
            time_since_last_poison = 1;
            return true;
        }else{
            time_since_last_poison++;
            return false;
        }
    }

    @Override
    public ArrayList<Event> special_trigger(Monster self, Monster opponent, Terrain terrain) {
        ArrayList<Event> events = new ArrayList<>();
        if(opponent.get_current_state() == null){
            if(this.will_it_poison()){
                MonsterState new_state = new PoisonState();
                opponent.apply_state(new_state);
                events.add(new NewStateAppliedEvent(new_state, opponent));
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

    @Override
    public TypeStats clone() {
        return new InsectStats();
    }
}
