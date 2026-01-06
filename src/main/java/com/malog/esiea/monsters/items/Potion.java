package com.malog.esiea.monsters.items;


import com.malog.esiea.monsters.game.event.Event;
import com.malog.esiea.monsters.game.event.MonsterHealedEvent;
import com.malog.esiea.monsters.monsters.Monster;

public class Potion extends Item{
    private final int heal_amount = 50;

    public Potion() {
        super();
    }

    @Override
    public Event use(Monster target) {
        return new MonsterHealedEvent(target, target.heal(heal_amount)); //TODO more precise event
    }

    @Override
    public String toString(){
        return "Potion (heals 50HP)";
    }
}
