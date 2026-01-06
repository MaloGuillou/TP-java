package com.malog.esiea.monsters.items;

import com.malog.esiea.monsters.game.event.Event;
import com.malog.esiea.monsters.game.event.StateEndedEvent;
import com.malog.esiea.monsters.monsters.Monster;

public class Medicine extends Item{

    public Medicine(){
        super();
    }

    @Override
    public Event use(Monster target) {
        Event event = null;
        if(target.get_current_state() != null){
            event =  new StateEndedEvent(target.get_current_state(), target);
            target.remove_state();
        }
        return event;
    }

    @Override
    public String toString(){
        return "Medicine (heals status)";
    }
}
