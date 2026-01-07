package com.malog.esiea.monsters.game.event;

import com.malog.esiea.monsters.monsters.Monster;

public class BurnDamageEvent extends Event{
    private final int damage;
    private final Monster monster;

    public BurnDamageEvent(Monster monster, int damage) {
        this.monster = monster;
        this.damage = damage;
    }

    @Override
    public String toString() {
        return monster.getName() + " is burning and receive " + damage + " damage";
    }
}
