package com.malog.esiea.monsters.states.monster;

import com.malog.esiea.monsters.game.event.Event;
import com.malog.esiea.monsters.game.event.PoisonDamageEvent;
import com.malog.esiea.monsters.game.event.StateEndedEvent;
import com.malog.esiea.monsters.monsters.Monster;
import com.malog.esiea.monsters.states.terrain.FloodedState;
import com.malog.esiea.monsters.terrains.Terrain;

public class PoisonState extends MonsterState{
    @Override
    public Event update_state(Monster self, Monster opponent, Terrain terrain) {
        if(terrain.getState() instanceof FloodedState){
            self.remove_state();
            return new StateEndedEvent(this, self);
        }

        int damage = self.getAttack()/10;
        self.apply_damage(damage);
        return new PoisonDamageEvent(self, damage);
    }

    @Override
    public String toString(){
        return "Poison";
    }
}
