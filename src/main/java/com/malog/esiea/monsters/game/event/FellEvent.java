package com.malog.esiea.monsters.game.event;

import com.malog.esiea.monsters.monsters.Monster;

public class FellEvent extends Event {
    private final Monster monster;
    private final int damage;

    public FellEvent(Monster monster, int damage) {
        this.monster = monster;
        this.damage = damage;
    }

    @Override
    public String toString() {
        return monster.getName() + " fell, and dealt " + damage + " damage to itself";
    }
}
