package com.malog.esiea.monsters.game.event;

import com.malog.esiea.monsters.monsters.Monster;
import com.malog.esiea.monsters.states.State;

public class NewStateAppliedEvent extends Event {
    private final State state_that_started;
    private final Monster monster;

    public NewStateAppliedEvent(State state_that_started, Monster monster){
        this.state_that_started = state_that_started;
        this.monster = monster;
    }

    @Override
    public String toString() {
        return monster.toString() + " is now suffering from " + state_that_started.toString();
    }
}
