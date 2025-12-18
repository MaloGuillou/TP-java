package com.malog.esiea.monsters.game.event;

import com.malog.esiea.monsters.monsters.Monster;
import com.malog.esiea.monsters.states.State;

public class StateEndedEvent extends Event {
    private final State state_that_ended;
    private final Monster monster;

    public StateEndedEvent(State state_that_ended, Monster monster){
        this.state_that_ended = state_that_ended;
        this.monster = monster;
    }
}
