package com.malog.esiea.monsters.monsters.types.stats;

import com.malog.esiea.monsters.game.event.Event;
import com.malog.esiea.monsters.game.event.NewStateAppliedEvent;
import com.malog.esiea.monsters.helpers.Randoms;
import com.malog.esiea.monsters.monsters.Monster;
import com.malog.esiea.monsters.states.monster.ParalysisState;
import com.malog.esiea.monsters.monsters.types.Type;
import com.malog.esiea.monsters.terrains.Terrain;

import java.util.ArrayList;

public class ElectricStats extends TypeStats{
    private final int paralysis_probability;

    public ElectricStats(int paralysis_probability) {
        super(Type.ELECTRIC);
        this.paralysis_probability = paralysis_probability;
    }

    public boolean will_it_paralyze(){
        int rnd = Randoms.get_random_int_in_range(0, 100);
        return rnd < this.paralysis_probability;
    }

    /**
     * Paralyze if opponent has no state and paralysis works
     * @param self do not matter here
     * @param opponent the monster that will get paralysis
     * @param terrain do not matter here
     * @return the list of event of what happened
     */
    @Override
    public ArrayList<Event> special_trigger(Monster self, Monster opponent, Terrain terrain) {
        ArrayList<Event> events = new ArrayList<>();

        if(opponent.get_current_state() == null){
            if(this.will_it_paralyze()){
                ParalysisState new_state = new ParalysisState();
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
}
